package heron.Boults;
/**
 * Created by Venkatesh on 11/11/2016.
 */
import backtype.storm.Config;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import heron.Tools.Rankings;
import heron.Tools.TupleHelpers;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This abstract bolt provides the basic behavior of bolts that rank objects according to their count.
public abstract class AbstractRanker extends BaseBasicBolt {

  private static final long serialVersionUID = 4931640198501530202L;
  private static final int DEFAULT_EMIT_FREQUENCY_IN_SECONDS = 2;
  private static final int DEFAULT_COUNT = 10;

  private final int emitFrequencyInSeconds;
  private final int count;
  private Rankings rankings;

  public AbstractRanker() {
    this(DEFAULT_COUNT, DEFAULT_EMIT_FREQUENCY_IN_SECONDS);
  }

  public AbstractRanker(int topN) {
    this(topN, DEFAULT_EMIT_FREQUENCY_IN_SECONDS);
  }

  public AbstractRanker(int topN, int emitFrequencyInSeconds) {
    if (topN < 1) {
      throw new IllegalArgumentException("topN must be >= 1 (you requested " + topN + ")");
    }
    if (emitFrequencyInSeconds < 1) {
      throw new IllegalArgumentException(
          "The emit frequency must be >= 1 seconds (you requested " + emitFrequencyInSeconds + " seconds)");
    }
    count = topN;
    this.emitFrequencyInSeconds = emitFrequencyInSeconds;
    rankings = new Rankings(count);
  }

  protected Rankings getRankings() {
    return rankings;
  }

  /**
   * This method functions as a template method (design pattern).
   */
  @Override
  public final void execute(Tuple tuple, BasicOutputCollector collector) {
    if (TupleHelpers.isTickTuple(tuple)) {
      getLogger().debug("Received tick tuple, triggering emit of current rankings");
      emitRankings(collector);
    }
    else {
      updateRankingsWithTuple(tuple);
    }
  }

  abstract void updateRankingsWithTuple(Tuple tuple);

  private void emitRankings(BasicOutputCollector collector) {
    collector.emit(new Values(rankings.copy()));
    String r=rankings.copy().toString();
    r=r.substring(1,r.length()-1);
    List<String> items = Arrays.asList(r.split(", "));
    try {
      FileWriter file = new FileWriter("D:\\venky\\downloads\\umkc_hackathon_heron\\src\\main\\java\\heron\\Visualization\\Hashtags.csv");
      file.write("hashtag" + "," + "count" + "\n");
      for(int i=0;i<items.size();i++){
        String l=items.get(i);
        if(l.startsWith("[#")){
          file.write(l.substring(2,l.length()-6) + "," +l.substring(l.length()-5,l.length()-4)  + "\n");
        }
      }
      file.flush();
      file.close();
    }catch (IOException e) {
      e.printStackTrace();
    }



    getLogger().debug("Rankings: " + rankings);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("rankings"));
  }

  @Override
  public Map<String, Object> getComponentConfiguration() {
    Map<String, Object> conf = new HashMap<String, Object>();
    conf.put(Config.TOPOLOGY_TICK_TUPLE_FREQ_SECS, emitFrequencyInSeconds);
    return conf;
  }

  abstract Logger getLogger();
}

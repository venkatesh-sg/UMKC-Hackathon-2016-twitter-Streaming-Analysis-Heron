package heron.Boults;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.testing.TestWordSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Venkatesh on 11/12/2016.
 */
public class SourceCount extends BaseRichBolt {
    private OutputCollector collector;

    // Map to store the count of the words
    private Map<String, Long> countMap;

    @Override
    public void prepare(
            Map                     map,
            TopologyContext         topologyContext,
            OutputCollector         outputCollector)
    {
        collector = outputCollector;
        countMap = new HashMap<String, Long>();
    }

    @Override
    public void execute(Tuple tuple) {
        // get the word from the 1st column of incoming tuple
        String word = tuple.getString(0);

        // check if the word is present in the map
        if (countMap.get(word) == null) {

            // not present, add the word with a count of 1
            countMap.put(word, 1L);
        } else {

            // already there, hence get the count
            Long val = countMap.get(word);

            // increment the count and save it to the map
            countMap.put(word, ++val);
        }
        collector.emit(new Values(word, countMap.get(word)));
        try {

            FileWriter file = new FileWriter("D:\\venky\\downloads\\umkc_hackathon_heron\\src\\main\\java\\heron\\Visualization\\OS.csv");
            file.write("count"+","+"Operating_System"+"\n");
            for(Map.Entry<String, Long> entry : countMap.entrySet()) {
                file.write(entry.getValue()+","+entry.getKey()+"\n");
            }
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer)
    {

        // tuple consists of a two columns called 'word' and 'count'
        outputFieldsDeclarer.declare(new Fields("word","count"));
    }
}

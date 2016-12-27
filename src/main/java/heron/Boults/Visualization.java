package heron.Boults;

/**
 * Created by Venkatesh on 11/13/2016.
 */
import backtype.storm.topology.base.BaseRichBolt;
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

import heron.Tools.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class Visualization extends BaseRichBolt{
    @Override
    public void prepare(
            Map                     map,
            TopologyContext         topologyContext,
            OutputCollector         outputCollector)
    {
    }

    @Override
    public void execute(Tuple tuple)
    {
        Rankings rankableList = (Rankings) tuple.getValue(0);
        try{
            FileWriter file = new FileWriter("PATH TO FILE\\src\\main\\java\\heron\\Visualization\\Hashtags.csv");
            file.write("hashtag" + "," + "count" + "\n");
            for (Rankable r: rankableList.getRankings()){
                String word = r.getObject().toString();
                Long count = r.getCount();
                file.write(word+","+Long.toString(count)+"\n");
            }
            file.flush();
            file.close();
        }catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        // nothing to add - since it is the final bolt
    }

}

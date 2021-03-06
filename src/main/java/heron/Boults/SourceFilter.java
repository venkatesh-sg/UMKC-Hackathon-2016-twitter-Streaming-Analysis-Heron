package heron.Boults;

/**
 * Created by Venkatesh on 11/12/2016.
 */
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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SourceFilter extends BaseRichBolt{
    OutputCollector collector;

    @Override
    public void prepare(
            Map                     map,
            TopologyContext         topologyContext,
            OutputCollector         outputCollector)
    {
        // save the output collector for emitting tuples
        collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple)
    {
        String tweet = tuple.getStringByField("Source").split("DELIMITER")[0];
        String[] sources = {"iphone","Android","Web"};
        String[] words =tweet.split(">");
        for (int i = 0; i <words.length ; i++) {
            if (words[i].contains("iPhone") || words[i].contains("Android") || words[i].contains("Web") || words[i].contains("Windows")) {
                if (words[i].contains("iPhone")) {
                    words[i] = "iOS";
                } else if (words[i].contains("Android")) {
                    words[i] = "Android";
                } else if (words[i].contains("Web")) {
                    words[i] = "Web";
                } else if (words[i].contains("Windows")) {
                    words[i] = "Windows";
                }
                collector.emit(new Values(words[i]));
            }
        }


    }
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        // tuple consists of a single column  'Hashtag'
        declarer.declare(new Fields("Source"));
    }
}

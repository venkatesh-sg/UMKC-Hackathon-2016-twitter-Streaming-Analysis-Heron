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
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisConnection;
import heron.Tools.*;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
public class Visualization extends BaseRichBolt{
    transient RedisConnection<String,String> redis;
    @Override
    public void prepare(
            Map                     map,
            TopologyContext         topologyContext,
            OutputCollector         outputCollector)
    {
        // instantiate a redis connection
        //Jedis client = new Jedis("localhost",6379);

        // initiate the actual connection
    }

    @Override
    public void execute(Tuple tuple)
    {
        Jedis client = new Jedis("localhost",6379);
        Rankings rankableList = (Rankings) tuple.getValue(0);

        for (Rankable r: rankableList.getRankings()){
            String word = r.getObject().toString();
            Long count = r.getCount();
            client.publish("pubsub", word + "|" + Long.toString(count));


        }

        // access the first column 'word'
        //String word = tuple.getStringByField("word");

        // access the second column 'count'
        //String word = rankedWords.toString();
        //Integer count = tuple.getIntegerByField("count");
        //Long count = new Long(100);

        // publish the word count to redis using word as the key
        //redis.publish("WordCountTopology", word + ":" + Long.toString(count));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        // nothing to add - since it is the final bolt
    }

}

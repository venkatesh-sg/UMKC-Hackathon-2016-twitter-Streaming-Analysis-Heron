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

import javax.json.JsonWriter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static javax.print.attribute.standard.MediaSizeName.D;

public class GeoLoc extends BaseRichBolt {
    //public List<Tuple> coords = new ArrayList<>();
    //JsonWriter jsonWriter = null;
    @Override
    public void prepare(
            Map                     map,
            TopologyContext         topologyContext,
            OutputCollector         outputCollector)
    {

    }
    public void execute(Tuple tuple)
    {
        
        JSONObject obj = new JSONObject();
        obj.put("Latitude",tuple.getValueByField("Latitude"));
        obj.put("Longitude",tuple.getValueByField("Longitude"));

        try {

            FileWriter file = new FileWriter("PATH TO FILE\\src\\main\\java\\heron\\Visualization\\GeoLoc.csv",true);
            file.write(tuple.getValueByField("Latitude")+","+tuple.getValueByField("Longitude")+"\n");
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer)
    {

    }


}

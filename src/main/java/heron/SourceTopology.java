package heron;

/**
 * Created by Venkatesh on 11/12/2016.
 */
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import heron.Boults.*;
import heron.Spouts.Spout;
import heron.Tools.Rankings;
import heron.Tools.RankableObjectWithFields;

import java.awt.*;
import java.io.File;
import java.util.LinkedList;

public class SourceTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new Spout("Consumer Key",
                "Consumer Secret",
                "Access Token",
                "Access Token Secret"));
        builder.setBolt("SourceFilter",new SourceFilter(),5).shuffleGrouping("spout");
        builder.setBolt("SourceCount",new SourceCount(),5).globalGrouping("SourceFilter");

        Config conf = new Config();
        conf.setDebug(true);
        conf.setMaxTaskParallelism(3);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Source",conf,builder.createTopology());
        File htmlFile = new File("PATH TO FILE\\src\\main\\java\\heron\\Visualization\\OS.html");
        Desktop.getDesktop().browse(htmlFile.toURI());


    }
}

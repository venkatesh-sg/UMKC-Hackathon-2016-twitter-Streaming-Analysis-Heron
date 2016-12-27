package heron;

/**
 * Created by Venkatesh on 11/12/2016.
 */
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import heron.Boults.*;
import heron.Spouts.GeoLocSpout;

import java.awt.*;
import java.io.File;

public class GeoLocTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("locspout", new GeoLocSpout("Consumer Key",
                "Consumer Secret",
                "Access Token",
                "Access Token Secret"));
        builder.setBolt("location", new GeoLoc()).shuffleGrouping("locspout");
        Config conf = new Config();
        conf.setDebug(true);
        conf.setMaxTaskParallelism(3);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Locations", conf, builder.createTopology());
        File htmlFile = new File("PATH TO FILE\\src\\main\\java\\heron\\Visualization\\HeatMap.html");
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
}

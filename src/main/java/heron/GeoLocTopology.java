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

        builder.setSpout("locspout", new GeoLocSpout("8xpDZGqUfNoUB45FrHzzi8B4L",
                "GYs9C9Qtwg0Vggq7bV3MkEkB2cKPKRVbtWKcb0z9hZHGMl8tGh",
                "591123770-CrvIWxQxaEWEkL2CT4TyTuIkLRBu0MKltNXINA0J",
                "a6OIxTo8Q25ubwyYhXsqsTidQQh6ANEcxm8Cq2uQ3Df0H"));
        builder.setBolt("location", new GeoLoc()).shuffleGrouping("locspout");
        Config conf = new Config();
        conf.setDebug(true);
        conf.setMaxTaskParallelism(3);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Locations", conf, builder.createTopology());
        //File htmlFile = new File("D:\\venky\\downloads\\umkc_hackathon_heron\\src\\main\\java\\heron\\Visualization\\HeatMap.html");
        //Desktop.getDesktop().browse(htmlFile.toURI());
    }
}

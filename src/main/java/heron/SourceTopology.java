package heron;

/**
 * Created by Venkatesh on 11/12/2016.
 */
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import heron.Boults.*;
import heron.Spouts.HashtagSpout;
import heron.Spouts.SourceSpout;
import heron.Tools.Rankings;
import heron.Tools.RankableObjectWithFields;
import java.util.LinkedList;

public class SourceTopology {
    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("spout", new SourceSpout("8xpDZGqUfNoUB45FrHzzi8B4L",
                "GYs9C9Qtwg0Vggq7bV3MkEkB2cKPKRVbtWKcb0z9hZHGMl8tGh",
                "591123770-CrvIWxQxaEWEkL2CT4TyTuIkLRBu0MKltNXINA0J",
                "a6OIxTo8Q25ubwyYhXsqsTidQQh6ANEcxm8Cq2uQ3Df0H"));
        builder.setBolt("SourceFilter",new SourceFilter(),5).shuffleGrouping("spout");
        builder.setBolt("SourceCount",new SourceCount(),5).globalGrouping("SourceFilter");

        Config conf = new Config();
        conf.setDebug(true);
        conf.setMaxTaskParallelism(3);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Source",conf,builder.createTopology());
        //File htmlFile = new File("D:\\venky\\downloads\\umkc_hackathon_heron\\src\\main\\java\\heron\\Visualization\\OS.html");
        //Desktop.getDesktop().browse(htmlFile.toURI());


    }
}

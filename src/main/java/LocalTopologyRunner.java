import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.metric.api.IClusterMetricsConsumer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;

public class LocalTopologyRunner {
    private static final int OVERFLOW_LIMIT = 600;

    public static void main(String[] args) {
        TopologyBuilder builder = new TopologyBuilder();

        builder.setSpout("commit-feed-listener", new CommitFeedListener());

        builder
                .setBolt("email-extractor", new EmailExtractor())
                .shuffleGrouping("commit-feed-listener");

        builder
                .setBolt("email-counter", new EmailCounter())
                .fieldsGrouping("email-extractor", new Fields("email"));

        Config config = new Config();
        config.setDebug(true);

        StormTopology topology = builder.createTopology();

        try {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("github-commit-count-topology",
                    config,
                    topology);

            Utils.sleep(OVERFLOW_LIMIT);
            cluster.killTopology("github-commit-count-topology");
            cluster.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

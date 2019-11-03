import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ClassLoaderObjectInputStream;
import org.apache.commons.lang.CharSet;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class CommitFeedListener extends BaseRichSpout {
    private SpoutOutputCollector spoutOutputCollector;
    private List<String> commits;

    /**
     * Gets called when Storm prepares spout to be run.
     * Reads
     * @param map
     * @param topologyContext
     * @param spoutOutputCollector
     */
    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.spoutOutputCollector = spoutOutputCollector;

        try {
            commits = IOUtils.readLines(
                    ClassLoader.getSystemResourceAsStream("changelog.txt"),
                    Charset.defaultCharset().name()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method called by Storm when it's readz to read next tuple for the spout.
     */
    @Override
    public void nextTuple() {
        for (String commit : commits) {
            spoutOutputCollector.emit(new Values(commit));
        }
    }

    /**
     * Define field names for all tuples emitted by the spout
     *
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("commit"));
    }
}

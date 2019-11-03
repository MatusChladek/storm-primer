import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class EmailExtractor extends BaseBasicBolt {

    /**
     * Gets called when tuple has been emitted to this bolt
     * @param tuple
     * @param basicOutputCollector
     */
    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String commit = tuple.getStringByField("commit");
        String[] parts = commit.split(" ");

        basicOutputCollector.emit(new Values(parts[1]));
    }

    /**
     * Define field names for all tuples emitted by the bolt
     * @param outputFieldsDeclarer
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("email"));
    }
}

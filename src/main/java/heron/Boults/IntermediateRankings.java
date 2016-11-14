package heron.Boults;


import backtype.storm.tuple.Tuple;
import heron.Tools.Rankable;
import heron.Tools.RankableObjectWithFields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This bolt ranks incoming objects by their count.
 * <p/>
 * It assumes the input tuples to adhere to the following format: (object, object_count, additionalField1,
 * additionalField2, ..., additionalFieldN).
 */
public final class IntermediateRankings extends AbstractRanker {

  private static final long serialVersionUID = -1369800530256637409L;
  private static final Logger LOG = LogManager.getLogger(IntermediateRankings.class);

  public IntermediateRankings() {
    super();
  }

  public IntermediateRankings(int topN) {
    super(topN);
  }

  public IntermediateRankings(int topN, int emitFrequencyInSeconds) {
    super(topN, emitFrequencyInSeconds);
  }

  @Override
  void updateRankingsWithTuple(Tuple tuple) {
    Rankable rankable = RankableObjectWithFields.from(tuple);
    super.getRankings().updateWith(rankable);
  }

  @Override
  Logger getLogger() {
    return LOG;
  }
}

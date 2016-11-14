package heron.Tools;
/**
 * Created by Venkatesh on 11/11/2016.
 */
import backtype.storm.tuple.Tuple;

class Constants {
  public static final String SYSTEM_COMPONENT_ID = "__system";
  public static final String SYSTEM_TICK_STREAM_ID = "__tick";
}

public final class TupleHelpers {

  private TupleHelpers() {
  }

  public static boolean isTickTuple(Tuple tuple) {
    return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID) && tuple.getSourceStreamId().equals(
        Constants.SYSTEM_TICK_STREAM_ID);
  }

}

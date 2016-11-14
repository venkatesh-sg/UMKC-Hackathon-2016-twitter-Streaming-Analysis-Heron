package heron.Tools;
/**
 * Created by Venkatesh on 11/11/2016.
 */
public interface Rankable extends Comparable<Rankable> {

  Object getObject();

  long getCount();

  /**
   * Note: We do not defensively copy the object wrapped by the Rankable.  It is passed as is.
   *
   * @return a defensive copy
   */
  Rankable copy();
}

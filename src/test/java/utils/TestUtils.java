package utils;

public class TestUtils
{
   private TestUtils() {};

   /*static public int argMax(double [] output) {
      int bestIndex = 0;

      for(int i = 0; i < output.length; i++) {
         if(output[i] > output[bestIndex]) {
            bestIndex = i;
         }
      }

      return bestIndex;
   }*/

   /*static public int depthFirstSearch(Scalar [] output) {
      AtomicInteger counter = new AtomicInteger(0);
      for (Scalar scalar : output) {
           depthFirstSearch(scalar, counter);
       }

      return counter.get();
   }*/

   /*static private void depthFirstSearch(Scalar scalar, AtomicInteger counter) {
      if(scalar.getParent() == null || scalar.getValue() == Double.MAX_VALUE) {
         return;
      }

      for(Scalar parent : scalar.getParent()) {
         depthFirstSearch(parent, counter);
      }

      counter.incrementAndGet();
   }*/
}

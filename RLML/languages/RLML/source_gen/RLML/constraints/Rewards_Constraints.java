package RLML.constraints;

/*Generated by MPS */

import jetbrains.mps.smodel.runtime.base.BaseConstraintsDescriptor;
import jetbrains.mps.smodel.runtime.base.BasePropertyConstraintsDescriptor;
import jetbrains.mps.smodel.runtime.ConstraintsDescriptor;
import org.jetbrains.mps.openapi.model.SNode;
import jetbrains.mps.smodel.runtime.CheckingNodeContext;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SPropertyOperations;
import jetbrains.mps.smodel.SNodePointer;
import java.util.ArrayList;
import jetbrains.mps.internal.collections.runtime.Sequence;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SNodeOperations;
import java.util.Map;
import org.jetbrains.mps.openapi.language.SProperty;
import jetbrains.mps.smodel.runtime.PropertyConstraintsDescriptor;
import java.util.HashMap;
import org.jetbrains.mps.openapi.language.SConcept;
import jetbrains.mps.smodel.adapter.structure.MetaAdapterFactory;

public class Rewards_Constraints extends BaseConstraintsDescriptor {
  public Rewards_Constraints() {
    super(CONCEPTS.Rewards$nI);
  }

  public static class Value_Property extends BasePropertyConstraintsDescriptor {
    public Value_Property(ConstraintsDescriptor container) {
      super(PROPS.value$lxjR, container, false, false, true);
    }
    @Override
    public boolean validateValue(SNode node, Object propertyValue, CheckingNodeContext checkingNodeContext) {
      boolean result = staticValidateProperty(node, SPropertyOperations.castString(propertyValue));
      if (!(result) && checkingNodeContext != null) {
        checkingNodeContext.setBreakingNode(new SNodePointer("r:12ad0550-f678-466c-bace-38d01386f6fd(RLML.constraints)", "5647291236746992992"));
      }
      return result;
    }
    private static boolean staticValidateProperty(SNode node, String propertyValue) {
      if (propertyValue.length() == 0) {
        return false;
      }
      // Remove all spaces, then remove first two open brackets [[, and last closed bracket ]
      String str = propertyValue.replaceAll("\\s+", "");
      str = str.substring(2, str.length() - 1);

      ArrayList<ArrayList<Integer>> rewardsArrLst = new ArrayList<ArrayList<Integer>>();

      // Split string based on remaining open brackets
      String[] strArr = str.split("\\[");
      for (String arr : strArr) {
        // For each string in the array, remove the closed bracket ], and the comma
        arr = arr.substring(0, arr.indexOf("]"));

        // Split each string based on comma to get the final string array
        String[] arrArr = arr.split(",");

        ArrayList<Integer> arrArrInt = new ArrayList<Integer>();
        for (int i = 0; i < arrArr.length; i++) {
          arrArrInt.add(Integer.parseInt(arrArr[i]));
        }
        rewardsArrLst.add(arrArrInt);
      }

      // Convert from ArrayList to int[][]
      int[][] rewards = new int[rewardsArrLst.size()][];
      for (int i = 0; i < rewards.length; i++) {
        rewards[i] = new int[rewardsArrLst.get(i).size()];
      }
      for (int i = 0; i < rewardsArrLst.size(); i++) {
        for (int j = 0; j < rewardsArrLst.get(i).size(); j++) {
          rewards[i][j] = rewardsArrLst.get(i).get(j);
        }
      }

      // Get the States Value to compare for validation
      String statesValue = SPropertyOperations.getString(Sequence.fromIterable(SNodeOperations.ofConcept(SNodeOperations.getChildren(SNodeOperations.getParent(node)), CONCEPTS.States$KN)).first(), PROPS.value$MHol);
      statesValue = statesValue.substring(1, statesValue.length() - 1);
      String[] states = statesValue.split(",");

      if (states.length == rewards.length) {
        return true;
      }

      return false;
    }
  }
  @Override
  protected Map<SProperty, PropertyConstraintsDescriptor> getSpecifiedProperties() {
    Map<SProperty, PropertyConstraintsDescriptor> properties = new HashMap<SProperty, PropertyConstraintsDescriptor>();
    properties.put(PROPS.value$lxjR, new Value_Property(this));
    return properties;
  }

  private static final class CONCEPTS {
    /*package*/ static final SConcept Rewards$nI = MetaAdapterFactory.getConcept(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x49c190188964fa77L, "RLML.structure.Rewards");
    /*package*/ static final SConcept States$KN = MetaAdapterFactory.getConcept(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x1d76fb9dad847c95L, "RLML.structure.States");
  }

  private static final class PROPS {
    /*package*/ static final SProperty value$lxjR = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x49c190188964fa77L, 0x49c190188964fa7aL, "value");
    /*package*/ static final SProperty value$MHol = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x1d76fb9dad847c95L, 0x1d76fb9dad847c96L, "value");
  }
}

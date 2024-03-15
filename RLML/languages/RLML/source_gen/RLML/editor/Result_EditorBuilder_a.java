package RLML.editor;

/*Generated by MPS */

import jetbrains.mps.editor.runtime.descriptor.AbstractEditorBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.mps.openapi.model.SNode;
import jetbrains.mps.openapi.editor.EditorContext;
import jetbrains.mps.openapi.editor.cells.EditorCell;
import jetbrains.mps.nodeEditor.cells.EditorCell_Collection;
import jetbrains.mps.nodeEditor.cellLayout.CellLayout_Indent;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SPropertyOperations;
import jetbrains.mps.nodeEditor.cells.EditorCell_Constant;
import jetbrains.mps.openapi.editor.style.Style;
import jetbrains.mps.editor.runtime.style.StyleImpl;
import jetbrains.mps.editor.runtime.style.StyleAttributes;
import jetbrains.mps.nodeEditor.cells.EditorCell_Component;
import javax.swing.JComponent;
import org.jetbrains.mps.openapi.language.SProperty;
import jetbrains.mps.smodel.adapter.structure.MetaAdapterFactory;

/*package*/ class Result_EditorBuilder_a extends AbstractEditorBuilder {
  @NotNull
  private SNode myNode;

  public Result_EditorBuilder_a(@NotNull EditorContext context, @NotNull SNode node) {
    super(context);
    myNode = node;
  }

  @NotNull
  @Override
  public SNode getNode() {
    return myNode;
  }

  /*package*/ EditorCell createCell() {
    return createCollection_0();
  }

  private EditorCell createCollection_0() {
    EditorCell_Collection editorCell = new EditorCell_Collection(getEditorContext(), myNode, new CellLayout_Indent());
    editorCell.setCellId("Collection_ouom3r_a");
    editorCell.setBig(true);
    setCellContext(editorCell);
    if (nodeCondition_ouom3r_a0a()) {
      editorCell.addEditorCell(createConstant_0());
    }
    editorCell.addEditorCell(createJComponent_0());
    return editorCell;
  }
  private boolean nodeCondition_ouom3r_a0a() {
    return SPropertyOperations.getString(myNode, PROPS.result$tVy0) == "" || SPropertyOperations.getString(myNode, PROPS.result$tVy0) == null;
  }
  private EditorCell createConstant_0() {
    EditorCell_Constant editorCell = new EditorCell_Constant(getEditorContext(), myNode, "result display here");
    editorCell.setCellId("Constant_ouom3r_a0");
    Style style = new StyleImpl();
    style.set(StyleAttributes.INDENT_LAYOUT_NEW_LINE, true);
    editorCell.getStyle().putAll(style);
    editorCell.setDefaultText("");
    return editorCell;
  }
  private EditorCell createJComponent_0() {
    EditorCell editorCell = EditorCell_Component.createComponentCell(getEditorContext(), myNode, _QueryFunction_JComponent_ouom3r_a1a(), "JComponent_ouom3r_b0");
    editorCell.setCellId("JComponent_ouom3r_b0_0");
    return editorCell;
  }
  private JComponent _QueryFunction_JComponent_ouom3r_a1a() {
    return ComponentFactory.createResultArea(myNode);
  }

  private static final class PROPS {
    /*package*/ static final SProperty result$tVy0 = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd92cL, 0x1fc7710a25a88d53L, "result");
  }
}

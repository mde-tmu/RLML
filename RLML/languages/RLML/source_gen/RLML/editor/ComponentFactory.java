package RLML.editor;

/*Generated by MPS */

import javax.swing.JComponent;
import jetbrains.mps.openapi.editor.EditorContext;
import org.jetbrains.mps.openapi.model.SNode;
import javax.swing.JButton;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SPropertyOperations;
import org.jetbrains.mps.openapi.module.SRepository;
import RLML.util.RunProgram;
import jetbrains.mps.lang.smodel.generator.smodelAdapter.SLinkOperations;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import jetbrains.mps.internal.collections.runtime.ListSequence;
import java.awt.Font;
import jetbrains.mps.nodeEditor.EditorSettings;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.JTextArea;
import java.io.FileReader;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import org.jetbrains.mps.openapi.language.SContainmentLink;
import jetbrains.mps.smodel.adapter.structure.MetaAdapterFactory;
import org.jetbrains.mps.openapi.language.SProperty;

public class ComponentFactory {
  public static JComponent createRunProgramButton(final EditorContext editorContext, final SNode node) {
    JButton button = ComponentFactory.createButton(node, editorContext, "Run Program", new Runnable() {
      @Override
      public void run() {
        if (SPropertyOperations.getBoolean(node, PROPS.languageSelection$TJMz)) {
          SRepository repository = editorContext.getRepository();
          StringBuilder result = RunProgram.runMyProgram(node, repository);
          SPropertyOperations.assign(SLinkOperations.getTarget(node, LINKS.result$uOrF), PROPS.result$tVy0, result.toString());
        } else {
          SRepository repository = editorContext.getRepository();
          StringBuilder result = RunProgram.runMyProgram_Python(node, repository);
          try {
            String pythonScriptPath = result.toString();
            SPropertyOperations.assign(SLinkOperations.getTarget(node, LINKS.result$uOrF), PROPS.result$tVy0, result.toString());
            ProcessBuilder processBuilder = new ProcessBuilder("python3", String.valueOf(pythonScriptPath));
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
              sb.append(line);
              sb.append(System.getProperty("line.separator"));
            }
            SPropertyOperations.assign(SLinkOperations.getTarget(node, LINKS.result$uOrF), PROPS.result$tVy0, sb.toString());

            int exitCode = process.waitFor();
            System.out.println("\nExited with error code : " + exitCode);
          } catch (IOException | InterruptedException e) {
            SPropertyOperations.assign(SLinkOperations.getTarget(node, LINKS.result$uOrF), PROPS.result$tVy0, e.toString());
            e.printStackTrace();
          }
        }
      }
    });
    return button;
  }
  public static JComponent createRunProgramButtonCompare(final EditorContext editorContext, final SNode node) {
    JButton button = ComponentFactory.createButton(node, editorContext, "Run Program", new Runnable() {
      @Override
      public void run() {

        SRepository repository = editorContext.getRepository();
        StringBuilder resultString = RunProgram.runMyProgramCompare(node, repository);
        String[] result = resultString.toString().split("STRINGENDSHEREBREAK");

        for (int i = 0; i < result.length; i++) {
          SPropertyOperations.assign(ListSequence.fromList(SLinkOperations.getChildren(node, LINKS.result$Y15E)).getElement(i), PROPS.result$tVy0, result[i]);
        }
      }
    });
    return button;
  }

  public static JComponent createLanguageSelectionButton(final EditorContext editorContext, final SNode node) {
    JButton button = ComponentFactory.createButton(node, editorContext, "Change Run Language", new Runnable() {
      @Override
      public void run() {
        SPropertyOperations.assign(node, PROPS.languageSelection$TJMz, !(SPropertyOperations.getBoolean(node, PROPS.languageSelection$TJMz)));
      }
    });
    return button;
  }

  public static JComponent createClearButton(final EditorContext editorContext, final SNode node) {
    JButton button = ComponentFactory.createButton(node, editorContext, "Clear Result", new Runnable() {
      @Override
      public void run() {
        SPropertyOperations.assign(SLinkOperations.getTarget(node, LINKS.result$uOrF), PROPS.result$tVy0, null);
      }
    });
    return button;
  }
  public static JComponent createClearButtonCompare(final EditorContext editorContext, final SNode node) {
    JButton button = ComponentFactory.createButton(node, editorContext, "Clear Result", new Runnable() {
      @Override
      public void run() {
        for (SNode result : ListSequence.fromList(SLinkOperations.getChildren(node, LINKS.result$Y15E))) {
          SPropertyOperations.assign(result, PROPS.result$tVy0, null);
        }
      }
    });
    return button;
  }

  public static JComponent createFileOptionButton(final EditorContext editorContext, final SNode node) {
    JButton button = ComponentFactory.createButton(node, editorContext, "CLICK HERE", new Runnable() {
      @Override
      public void run() {
        SPropertyOperations.assign(node, PROPS.useFile$Lt8q, !(SPropertyOperations.getBoolean(node, PROPS.useFile$Lt8q)));
      }
    });
    button.setFont(new Font(EditorSettings.getInstance().getFontFamily(), Font.PLAIN, EditorSettings.getInstance().getFontSize() * 15 / 20));

    return button;
  }
  public static JComponent createFileOptionButtonCompare(final EditorContext editorContext, final SNode node) {
    JButton button = ComponentFactory.createButton(node, editorContext, "CLICK HERE", new Runnable() {
      @Override
      public void run() {
        SPropertyOperations.assign(node, PROPS.useFile$XUKf, !(SPropertyOperations.getBoolean(node, PROPS.useFile$XUKf)));
      }
    });
    button.setFont(new Font(EditorSettings.getInstance().getFontFamily(), Font.PLAIN, EditorSettings.getInstance().getFontSize() * 15 / 20));

    return button;
  }


  public static JComponent createBrowseFileButton(final EditorContext editorContext, final SNode node) {
    SPropertyOperations.assign(node, PROPS.displayFileStatus$v80n, "");
    JButton button = ComponentFactory.createButton(node, editorContext, "Browse File", new Runnable() {
      @Override
      public void run() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          if (selectedFile.getName().toLowerCase().endsWith(".txt")) {
            readDataFromFile(selectedFile, node, null);
            SPropertyOperations.assign(node, PROPS.useFile$Lt8q, false);
          } else {
            SPropertyOperations.assign(node, PROPS.displayFileStatus$v80n, "Selected is not a .txt file");
          }
        }
      }
    });
    button.setFont(new Font(EditorSettings.getInstance().getFontFamily(), Font.PLAIN, EditorSettings.getInstance().getFontSize() * 15 / 20));
    return button;
  }
  public static JComponent createBrowseFileButtonCompare(final EditorContext editorContext, final SNode node) {
    SPropertyOperations.assign(node, PROPS.displayFileStatus$XUZg, "");
    JButton button = ComponentFactory.createButton(node, editorContext, "Browse File", new Runnable() {
      @Override
      public void run() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          if (selectedFile.getName().toLowerCase().endsWith(".txt")) {
            readDataFromFile(selectedFile, null, node);
            SPropertyOperations.assign(node, PROPS.useFile$XUKf, false);
          } else {
            SPropertyOperations.assign(node, PROPS.displayFileStatus$XUZg, "Selected is not a .txt file");
          }
        }
      }
    });
    button.setFont(new Font(EditorSettings.getInstance().getFontFamily(), Font.PLAIN, EditorSettings.getInstance().getFontSize() * 15 / 20));
    return button;
  }

  public static JTextArea createResultArea(final SNode node) {
    JTextArea textArea = new JTextArea(SPropertyOperations.getString(node, PROPS.result$tVy0));
    textArea.setEditable(false);
    return textArea;

  }

  private static int[][] convertToArray(String arrayString) {
    String[] rows = arrayString.strip().substring(2, arrayString.length() - 2).split("\\],\\s*\\[");

    int[][] array = new int[rows.length][];

    for (int i = 0; i < rows.length; i++) {
      String[] elements = rows[i].split(",");
      array[i] = new int[elements.length];

      for (int j = 0; j < elements.length; j++) {
        if (elements[j].strip() != "") {
          array[i][j] = Integer.parseInt(elements[j].strip());
        }
      }
    }
    return array;
  }

  private static void readDataFromFile(File file, final SNode node, final SNode nodeComparator) {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      String states = "";
      String actions = "";
      String rewards = "";
      String doneStates = "";

      StringBuilder strBuild = new StringBuilder();

      String[] statesArray = {};
      int[][] actionsArray = {};
      int[][] rewardsArray = {};
      String[] doneArray = {};



      while ((line = br.readLine()) != null) {
        if (line.toLowerCase().startsWith("states")) {
          states = line.replaceAll("(?i)states\\s*:?\\s*", "");
          statesArray = states.split(",");
        } else if (line.toLowerCase().startsWith("actions")) {
          actions = line.replaceAll("(?i)actions\\s*:?\\s*", "");
          actionsArray = convertToArray(actions.strip());
        } else if (line.toLowerCase().startsWith("rewards")) {
          rewards = line.replaceAll("(?i)rewards\\s*:?\\s*", "");
          rewardsArray = convertToArray(rewards.strip());
        } else if (line.toLowerCase().matches("(?i)done\\s?states.*")) {
          doneStates = line.replaceAll("(?i)done\\s?states\\s*:?\\s*", "");
          doneArray = doneStates.split(",");
        }
      }

      strBuild.append("States: " + statesArray.length);
      strBuild.append(System.getProperty("line.separator"));
      strBuild.append("Actions " + actionsArray.length);
      strBuild.append(System.getProperty("line.separator"));
      strBuild.append("Rewards " + rewardsArray.length);
      strBuild.append(System.getProperty("line.separator"));
      strBuild.append("Done States " + doneArray.length);
      SPropertyOperations.assign(SLinkOperations.getTarget(node, LINKS.result$uOrF), PROPS.result$tVy0, strBuild.toString());


      Boolean isValid = false;
      if (statesArray.length != 0 && actionsArray.length != 0 && rewardsArray.length != 0 && doneArray.length != 0) {
        isValid = statesArray.length == actionsArray.length && statesArray.length == rewardsArray.length;
      } else {
        if (node != null) {
          SPropertyOperations.assign(node, PROPS.displayFileStatus$v80n, "Please check you file, the states are more or less than actions or rewards");
        } else if (nodeComparator != null) {
          SPropertyOperations.assign(nodeComparator, PROPS.displayFileStatus$XUZg, "Please check you file, the states are more or less than actions or rewards");
        }
      }

      if (node != null && isValid) {
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(node, LINKS.environment$uuBe), LINKS.States$5N_0), PROPS.value$MHol, states);
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(node, LINKS.environment$uuBe), LINKS.Actions$5O32), PROPS.value$MI6P, actions);
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(node, LINKS.environment$uuBe), LINKS.Rewards$5OK5), PROPS.value$lxjR, rewards);
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(node, LINKS.environment$uuBe), LINKS.DoneStates$VCEj), PROPS.value$lFLH, doneStates);
        SPropertyOperations.assign(node, PROPS.displayFileStatus$v80n, "Selected file successfully processed, make sure data constraints are followed");
      }

      if (nodeComparator != null && isValid) {
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(nodeComparator, LINKS.environment$XVGj), LINKS.States$5N_0), PROPS.value$MHol, states);
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(nodeComparator, LINKS.environment$XVGj), LINKS.Actions$5O32), PROPS.value$MI6P, actions);
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(nodeComparator, LINKS.environment$XVGj), LINKS.Rewards$5OK5), PROPS.value$lxjR, rewards);
        SPropertyOperations.assign(SLinkOperations.getTarget(SLinkOperations.getTarget(nodeComparator, LINKS.environment$XVGj), LINKS.DoneStates$VCEj), PROPS.value$lFLH, doneStates);
        SPropertyOperations.assign(nodeComparator, PROPS.displayFileStatus$XUZg, "Selected file successfully processed, make sure data constraints are followed");
      }

    } catch (IOException e) {
      e.printStackTrace();
      if (node != null) {
        SPropertyOperations.assign(node, PROPS.displayFileStatus$v80n, "Invalid file data please try again with a different file");

      } else {
        SPropertyOperations.assign(nodeComparator, PROPS.displayFileStatus$XUZg, "Invalid file data please try again with a different file");
      }
    }
  }

  private static JButton createButton(final SNode node, final EditorContext editorContext, String title, final Runnable action) {
    JButton button = new JButton(title);
    button.setFont(new Font(EditorSettings.getInstance().getFontFamily(), Font.PLAIN, EditorSettings.getInstance().getFontSize()));
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent p0) {
        editorContext.getRepository().getModelAccess().executeCommandInEDT(action);
      }
    });
    return button;
  }

  private static final class LINKS {
    /*package*/ static final SContainmentLink result$uOrF = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd8f1L, 0x3a3770586b503fccL, "result");
    /*package*/ static final SContainmentLink result$Y15E = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x10f517300f75a927L, 0x10f517300f75a949L, "result");
    /*package*/ static final SContainmentLink environment$uuBe = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd8f1L, 0x3a3770586b503faeL, "environment");
    /*package*/ static final SContainmentLink States$5N_0 = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd92aL, 0x49c190188964fa7cL, "States");
    /*package*/ static final SContainmentLink Actions$5O32 = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd92aL, 0x49c190188964fa7eL, "Actions");
    /*package*/ static final SContainmentLink Rewards$5OK5 = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd92aL, 0x49c190188964fa81L, "Rewards");
    /*package*/ static final SContainmentLink DoneStates$VCEj = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd92aL, 0x7adf9c592617f62dL, "DoneStates");
    /*package*/ static final SContainmentLink environment$XVGj = MetaAdapterFactory.getContainmentLink(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x10f517300f75a927L, 0x10f517300f75a947L, "environment");
  }

  private static final class PROPS {
    /*package*/ static final SProperty result$tVy0 = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd92cL, 0x1fc7710a25a88d53L, "result");
    /*package*/ static final SProperty languageSelection$TJMz = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd8f1L, 0x664b083071203caeL, "languageSelection");
    /*package*/ static final SProperty useFile$Lt8q = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd8f1L, 0x10f517300f490204L, "useFile");
    /*package*/ static final SProperty useFile$XUKf = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x10f517300f75a927L, 0x10f517300f75a943L, "useFile");
    /*package*/ static final SProperty displayFileStatus$v80n = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x4613d414d7bcd8f1L, 0x10f517300f50b65bL, "displayFileStatus");
    /*package*/ static final SProperty displayFileStatus$XUZg = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x10f517300f75a927L, 0x10f517300f75a944L, "displayFileStatus");
    /*package*/ static final SProperty value$MHol = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x1d76fb9dad847c95L, 0x1d76fb9dad847c96L, "value");
    /*package*/ static final SProperty value$MI6P = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x1d76fb9dad847c98L, 0x1d76fb9dad847c99L, "value");
    /*package*/ static final SProperty value$lxjR = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x49c190188964fa77L, 0x49c190188964fa7aL, "value");
    /*package*/ static final SProperty value$lFLH = MetaAdapterFactory.getProperty(0x3c2f74fb565a4cb8L, 0x8a8142024cc7aa10L, 0x7adf9c592617f60eL, 0x7adf9c592617f61dL, "value");
  }
}

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Hoku extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        // Get all the required data from data keys
        Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        Project project = event.getRequiredData(CommonDataKeys.PROJECT);
        Document document = editor.getDocument();

        // Work off of the primary caret to get the selection info
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();
        Scanner sc = new Scanner(System.in, "cp1251");
        PrintStream ps = null;
        try {
            ps = new PrintStream(System.out, false, "cp1251");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String word = editor.getSelectionModel().getSelectedText();

        Translator translator = new Translator();
        String ans = null;
        try {
            ans = translator.translate(word);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //Messages.showMessageDialog(ans,"123",Messages.getInformationIcon());
        // Replace the selection with a fixed string.
        // Must do this document change in a write action context.
        String finalAns = ans;
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, finalAns)
        );

        // De-select the text range that was just replaced
        primaryCaret.removeSelection();
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        // Get required data keys
        Project project = event.getProject();
        Editor editor = event.getData(CommonDataKeys.EDITOR);

        // Set visibility only in the case of
        // existing project editor, and selection
        event.getPresentation().setEnabledAndVisible(project != null
                && editor != null && editor.getSelectionModel().hasSelection());
    }

    @Override
    public boolean isDumbAware() {
        return super.isDumbAware();
    }
}

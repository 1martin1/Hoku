import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class Hoku extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        var edit = e.getData(PlatformDataKeys.EDITOR);
        String str = edit.getSelectionModel().getSelectedText();

        int strNumber = 1;
        int glasCount = 0;
        boolean isHoku = true;

        for (int i = 0; i < str.length(); ++i){
            switch (str.charAt(i)){
                case 'a':
                    glasCount++;
                    break;
                case 'o':
                    glasCount++;
                    break;
                case 'u':
                    glasCount++;
                    break;
                case 'e':
                    glasCount++;
                    break;
                case 'i':
                    glasCount++;
                    break;
                case 'y':
                    glasCount++;
                    break;
                case '\n':
                    if (strNumber == 2){
                        if (glasCount != 7){
                            isHoku = false;
                        }
                    }else{
                        if (glasCount != 5){
                            isHoku = false;
                        }
                    }
                    strNumber++;
                    glasCount = 0;
            }
        }

        if (isHoku){
            Messages.showMessageDialog("It is Hoku, congrac", "Hoku Checker", Messages.getInformationIcon());
        }else{
            BrowserUtil.browse("https://en.wikipedia.org/wiki/Haiku");
            //Messages.showMessageDialog("It isn't Hoku", "Hoku Checker", Messages.getInformationIcon());
        }
    }

    @Override
    public boolean isDumbAware() {
        return super.isDumbAware();
    }
}

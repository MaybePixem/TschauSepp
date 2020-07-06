package View;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;

public abstract class InputViewHelper {

    protected static void addLabelToLayout(JLabel component, int yPos, Container containingPanel) {

        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
        gridBagConstraintForLabel.gridx = 0;
        gridBagConstraintForLabel.gridy = yPos;
        containingPanel.add(component, gridBagConstraintForLabel);
    }

    protected static void addComponentToLayout(JComponent component, int yPos, Container containingPanel) {

        GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
        gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
        gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 0);
        gridBagConstraintForLabel.gridx = 1;
        gridBagConstraintForLabel.gridy = yPos;
        containingPanel.add(component, gridBagConstraintForLabel);
    }

    protected static void makeDigitsOnlySpinnerUsingDocumentFilter(JSpinner spinner) {
        JSpinner.NumberEditor jsEditor = (JSpinner.NumberEditor) spinner.getEditor();
        final Document jsDoc = jsEditor.getTextField().getDocument();
        if (jsDoc instanceof PlainDocument) {
            AbstractDocument doc = new PlainDocument() {

                private static final long serialVersionUID = 1L;

                @Override
                public void setDocumentFilter(DocumentFilter filter) {
                    if (filter instanceof SpinnerNumberFilter) {
                        super.setDocumentFilter(filter);
                    }
                }
            };
            doc.setDocumentFilter(new SpinnerNumberFilter());
            jsEditor.getTextField().setDocument(doc);
        }
    }

}

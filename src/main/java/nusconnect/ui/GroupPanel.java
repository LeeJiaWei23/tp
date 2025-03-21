package nusconnect.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import nusconnect.commons.core.LogsCenter;

/**
 * The UI component that is responsible for containing the list of groups.
 */
public class GroupPanel extends UiPart<Region> {
    private static final String FXML = "GroupPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(GroupPanel.class);

    @FXML
    private ListView<String> groupListView;

    /**
     * Creates a {@code GroupPanel}.
     */
    public GroupPanel() {
        super(FXML);
        groupListView.getStyleClass().add("group-list");
        groupListView.getItems().addAll("Work in Progress");
        // dummy values
    }
}

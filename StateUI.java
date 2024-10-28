import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.HashMap;

public class StateUI {
    private JFrame frame;
    private JTextArea textArea;
    private MyHashMap<State, StateInfo> stateMap;

    public StateUI() {
        stateMap = new MyHashMap<>();
        initializeStateData(); // Initialize with some state data
        initializeUI();
    }

    private void initializeStateData() {
        State california = new State("California", "CA");
        StateInfo californiaInfo = new StateInfo("Sacramento", 39538223, 163696.32);
        californiaInfo.addLandmarkPicture("https://upload.wikimedia.org/wikipedia/commons/a/a7/Golden_Gate_Bridge%2C_San_Francisco%2C_CA.jpg");
        californiaInfo.addLandmarkPicture("https://upload.wikimedia.org/wikipedia/commons/e/ec/Yosemite_National_Park_%28cropped%29.jpg");
        stateMap.put(california, californiaInfo);

        State texas = new State("Texas", "TX");
        StateInfo texasInfo = new StateInfo("Austin", 29145505, 268596.46);
        texasInfo.addLandmarkPicture("https://upload.wikimedia.org/wikipedia/commons/0/00/Alamo_Exterior.jpg");
        texasInfo.addLandmarkPicture("https://upload.wikimedia.org/wikipedia/commons/0/0e/Space_Center_Houston_5.jpg");
        stateMap.put(texas, texasInfo);

        State newYork = new State("New York", "NY");
        StateInfo newYorkInfo = new StateInfo("Albany", 20201249, 54555.39);
        newYorkInfo.addLandmarkPicture("https://w7.pngwing.com/pngs/524/107/png-transparent-statue-of-liberty-new-york-new-york-city-cities-skylines-news-building-city-skyscraper-thumbnail.png");
        newYorkInfo.addLandmarkPicture("https://mcscoring.com/AndroidClass/spartans.png");
        stateMap.put(newYork, newYorkInfo);
    }

    private void initializeUI() {
        frame = new JFrame("State Information App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton viewAllButton = new JButton("View All States");
        viewAllButton.addActionListener(e -> viewAllStates());
        panel.add(viewAllButton);

        JButton viewInfoButton = new JButton("View State Info");
        viewInfoButton.addActionListener(e -> viewStateInfo());
        panel.add(viewInfoButton);

        JButton addPictureButton = new JButton("Add Picture");
        addPictureButton.addActionListener(e -> addPicture());
        panel.add(addPictureButton);

        JButton deleteStateButton = new JButton("Delete State");
        deleteStateButton.addActionListener(e -> deleteState());
        panel.add(deleteStateButton);

        JButton addStateButton = new JButton("Add State");
        addStateButton.addActionListener(e -> addState());
        panel.add(addStateButton);

        frame.add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void viewAllStates() {
        StringBuilder sb = new StringBuilder("States:\n");
        for (State state : stateMap.keySet()) {
            sb.append(state.getName()).append(" (").append(state.getAbbreviation()).append(")\n");
        }
        textArea.setText(sb.toString());
    }

    private void viewStateInfo() {
        String abbreviation = JOptionPane.showInputDialog(frame, "Enter State Abbreviation:");
        if (abbreviation != null) {
            State state = new State("", abbreviation.toUpperCase());
            StateInfo info = stateMap.get(state);
            if (info != null) {
                textArea.setText(info.toString());
                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new GridLayout(0, 1)); // One column for images

                // Limit images to a maximum of 2
                int count = 0;
                for (String url : info.getLandmarkPictures()) {
                    if (count < 2) {
                        try {
                            ImageIcon icon = new ImageIcon(new URL(url));
                            if (icon.getIconWidth() > 0) { // Check if image loaded
                                JLabel label = new JLabel(icon);
                                imagePanel.add(label);
                                count++;
                            } else {
                                System.err.println("Image not found or invalid URL: " + url);
                            }
                        } catch (Exception e) {
                            System.err.println("Failed to load image from URL: " + url);
                            e.printStackTrace();
                        }
                    }
                }

                // Show images in a dialog
                if (count > 0) {
                    JOptionPane.showMessageDialog(frame, imagePanel, "State Info and Pictures", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "No pictures available for this state.", "State Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                textArea.setText("State not found!");
            }
        }
    }

    private void addPicture() {
        String abbreviation = JOptionPane.showInputDialog(frame, "Enter State Abbreviation:");
        if (abbreviation != null) {
            String url = JOptionPane.showInputDialog(frame, "Enter Picture URL:");
            if (url != null) {
                State state = new State("", abbreviation.toUpperCase());
                StateInfo info = stateMap.get(state);
                if (info != null) {
                    // Check if there are already 2 pictures
                    if (info.getLandmarkPictures().size() < 2) {
                        info.addLandmarkPicture(url);
                        textArea.setText("Picture added!");
                    } else {
                        textArea.setText("Cannot add more than 2 pictures for this state!");
                    }
                } else {
                    textArea.setText("State not found!");
                }
            }
        }
    }

    private void deleteState() {
        String abbreviation = JOptionPane.showInputDialog(frame, "Enter State Abbreviation:");
        if (abbreviation != null) {
            State state = new State("", abbreviation.toUpperCase());
            if (stateMap.remove(state) != null) {
                textArea.setText("State deleted!");
            } else {
                textArea.setText("State not found!");
            }
        }
    }

    private void addState() {
        String name = JOptionPane.showInputDialog(frame, "Enter State Name:");
        String abbreviation = JOptionPane.showInputDialog(frame, "Enter State Abbreviation:");
        String capital = JOptionPane.showInputDialog(frame, "Enter State Capital:");
        long population = Long.parseLong(JOptionPane.showInputDialog(frame, "Enter State Population:"));
        double size = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter State Size (in square miles):"));

        State newState = new State(name, abbreviation.toUpperCase());
        StateInfo newStateInfo = new StateInfo(capital, population, size);

        // Remove old entry if it exists
        stateMap.remove(newState);
        stateMap.put(newState, newStateInfo);
        textArea.setText("State added/updated!");
    }
}


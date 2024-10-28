import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
public class StateUI {
    private JFrame frame;
    private JTextArea textArea;
    private MyHashMap<State, StateInfo> stateMap;

    public StateUI() {
        stateMap = new MyHashMap<>();
        initializeStateData(); 
        initializeUI();
        viewAllStates();
    }

    private void initializeStateData() {
        State california = new State("California", "CA");
        StateInfo californiaInfo = new StateInfo("Sacramento", 39538223, 163696.32);
        californiaInfo.addLandmarkPicture("https://media.istockphoto.com/id/1137980721/photo/golden-gate-bridge.jpg?s=612x612&w=0&k=20&c=UvJBPbFEA9LiHPfZ8joZ0zrIJzkojm6FvBgzhPjDxrA=");
        californiaInfo.addLandmarkPicture("https://upload.wikimedia.org/wikipedia/commons/f/f8/Bixby_Bridge_Big_Sur_California_United_States_Landscape_Photography_%28106473129%29.jpeg");
        stateMap.put(california, californiaInfo);

        State texas = new State("Texas", "TX");
        StateInfo texasInfo = new StateInfo("Austin", 29145505, 268596.46);
        texasInfo.addLandmarkPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTnXSANIbWUUuymEU7YQqHrJGvC5kwMaDSAcQ&s");
        texasInfo.addLandmarkPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNjubPBxNzG-sQwvnc1_-UnkrjLlv8K3V1gg&s");
        stateMap.put(texas, texasInfo);

        State newYork = new State("New York", "NY");
        StateInfo newYorkInfo = new StateInfo("Albany", 20201249, 54555.39);
        newYorkInfo.addLandmarkPicture("https://w7.pngwing.com/pngs/524/107/png-transparent-statue-of-liberty-new-york-new-york-city-cities-skylines-news-building-city-skyscraper-thumbnail.png");
        newYorkInfo.addLandmarkPicture("https://mcscoring.com/AndroidClass/spartans.png");
        stateMap.put(newYork, newYorkInfo);
        State utah = new State("Utah", "UT");
        StateInfo utahInfo = new StateInfo("Salt Lake City", 3417734, 84900);
        utahInfo.addLandmarkPicture("https://media.istockphoto.com/id/108223280/photo/delicate-arch.jpg?s=612x612&w=0&k=20&c=GrlyxxY1AMozB58qAOZ8Pf9QWHkJU2tUjHOOBp-VJNM=");
        utahInfo.addLandmarkPicture("https://images.squarespace-cdn.com/content/v1/5bb674d7a0cd273dc676b5e2/1565725607801-9F574387F95ZHITP6QH1/image-asset.jpeg");
        stateMap.put(utah, utahInfo);
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
                    try {
                        URI uri = URI.create(url); // Create a URI from the URL string
                        URL imageUrl = uri.toURL(); // Convert the URI to a URL
                        ImageIcon icon = new ImageIcon(imageUrl);
                        
                        if (icon.getIconWidth() > 0) { // Check if image loaded
                            // Resize the image
                            Image scaledImage = icon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                            JLabel label = new JLabel(new ImageIcon(scaledImage)); // Use scaled image
                            
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
                  
                        info.addLandmarkPicture(url);
                        textArea.setText("Picture added!");
                    
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


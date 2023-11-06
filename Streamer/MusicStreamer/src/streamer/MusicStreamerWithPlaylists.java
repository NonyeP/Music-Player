package streamer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicStreamerWithPlaylists {
    private Map<String, List<File>> playlists;
    private String currentPlaylistName;
    private int currentIndex;
    private Clip clip;
    private JFrame frame;//using frame because i didnt extend jframe from class declaration

    public MusicStreamerWithPlaylists() {
        clip = createClip();
        playlists = createDefaultPlaylists();

        // Create the GUI
        frame = new JFrame("Music Player");
        //frame.setSize(600,700);// clip makes the size defination irrelevant
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(4, 1));
        //frame.getContentPane().setBackground(new Color(255,0,173)); other buttons like play covers it, so it is irrelevant to have

        ImageIcon image = new ImageIcon("C:\\Users\\Paul\\Desktop\\Streamer\\MusicStreamer\\src\\streamer\\LetterM2.png");
        frame.setIconImage(image.getImage());//get icon of image

        Border border = BorderFactory.createLineBorder(Color.GREEN);
        JComboBox<String> playlistSelector = new JComboBox<>();
        for (String playlistName : playlists.keySet()) {
            playlistSelector.addItem(playlistName);
        }


        playlistSelector.setBackground(Color.LIGHT_GRAY);
        playlistSelector.setForeground(Color.BLACK);//set font colour
        playlistSelector.setFont(new Font("MV Boli",Font.PLAIN,16));
        playlistSelector.setOpaque(true);
        playlistSelector.setBorder(border);
        playlistSelector.setBounds(20, 40, 80, 30);



        JButton playButton = new JButton("Play");
        JButton stopButton = new JButton("Stop");
        JButton nextButton = new JButton("Next");
        JButton exitButton = new JButton("Exit");
        JButton renameButton = new JButton("Rename Playlist");


        playButton.setBackground(Color.LIGHT_GRAY);
        playButton.setForeground(Color.BLACK);//set font colour
        playButton.setFont(new Font("MV Boli",Font.PLAIN,16));
        playButton.setOpaque(true);
        playButton.setBorder(border);
        playButton.setBounds(20, 40, 80, 30);

        stopButton.setBackground(Color.LIGHT_GRAY);
        stopButton.setForeground(Color.BLACK);//set font colour
        stopButton.setFont(new Font("MV Boli",Font.PLAIN,16));
        stopButton.setOpaque(true);
        stopButton.setBorder(border);
        stopButton.setBounds(20, 40, 80, 30);


        nextButton.setBackground(Color.LIGHT_GRAY);
        nextButton.setForeground(Color.BLACK);//set font colour
        nextButton.setFont(new Font("MV Boli",Font.PLAIN,16));
        nextButton.setOpaque(true);
        nextButton.setBorder(border);
        nextButton.setBounds(20, 40, 80, 30);

        exitButton.setBackground(Color.LIGHT_GRAY);
        exitButton.setForeground(Color.BLACK);//set font colour
        exitButton.setFont(new Font("MV Boli",Font.PLAIN,16));
        exitButton.setOpaque(true);
        exitButton.setBorder(border);
        exitButton.setBounds(20, 40, 80, 30);

        renameButton.setBackground(Color.LIGHT_GRAY);
        renameButton.setForeground(Color.BLACK);//set font colour
        renameButton.setFont(new Font("MV Boli",Font.PLAIN,16));
        renameButton.setOpaque(true);
        renameButton.setBorder(border);
        renameButton.setBounds(20, 40, 80, 30);



        playlistSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentPlaylistName = (String) playlistSelector.getSelectedItem();
                currentIndex = 0;
                stop();
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                next();
            }
        });

        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renamePlaylist();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.add(playlistSelector);
        frame.add(playButton);
        frame.add(stopButton);
        frame.add(nextButton);
        frame.add(renameButton);
        frame.add(exitButton);

        frame.pack();
        frame.setVisible(true);
    }

    private Map<String, List<File>> createDefaultPlaylists() {
        Map<String, List<File>> playlists = new HashMap<>();
        List<File> defaultPlaylist = new ArrayList<>();
        // Add your music files to the default playlist (provide the file paths)
        defaultPlaylist.add(new File("path"));
        defaultPlaylist.add(new File("path"));
        playlists.put("Default Playlist", defaultPlaylist);
        playlists.put("Playlist-E", defaultPlaylist);
        playlists.put("Playlist-D", defaultPlaylist);
        playlists.put("Playlist-C", defaultPlaylist);
        playlists.put("Playlist-B", defaultPlaylist);
        playlists.put("Playlist-A", defaultPlaylist);
        currentPlaylistName = "Default Playlist";
        return playlists;
    }

    private void renamePlaylist() {
        String newPlaylistName = JOptionPane.showInputDialog(frame, "Enter the new playlist name:");
        if (newPlaylistName != null && !newPlaylistName.isEmpty()) {
            playlists.put(newPlaylistName, playlists.remove(currentPlaylistName));
            currentPlaylistName = newPlaylistName;
            refreshPlaylistSelector();
        }
    }

    private void refreshPlaylistSelector() {
        JComboBox<String> playlistSelector = (JComboBox<String>) frame.getContentPane().getComponent(0);
        playlistSelector.removeAllItems();
        for (String playlistName : playlists.keySet()) {
            playlistSelector.addItem(playlistName);
        }
        playlistSelector.setSelectedItem(currentPlaylistName);
    }

//    private void createDefaultPlaylist() {
//        List<File> defaultPlaylist = new ArrayList<>();
//        // Add your music files to the other playlist (provide the file paths)
//        defaultPlaylist.add(new File("path_to_music_file_1.mp3"));
//        defaultPlaylist.add(new File("path_to_music_file_2.mp3"));
//        playlists.put("Default Playlist", defaultPlaylist);
//        currentPlaylistName = "Default Playlist";
//    }

    private Clip createClip() {
        try {
            return AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void play() {
        try {
            File audioFile = new File("path"); // Replace with your audio file path
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip.open(audioInputStream);
            clip.start();
            System.out.println("Playing...");
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }


//        if (clip.isOpen()) {
//            clip.close();
//        }
//        List<File> currentPlaylist = playlists.get(currentPlaylistName);
//        if (currentPlaylist != null && currentIndex >= 0 && currentIndex < currentPlaylist.size()) {
//            File currentMusicFile = currentPlaylist.get(currentIndex);
//            try {
//                AudioInputStream audioInputStreamer = AudioSystem.getAudioInputStream(currentMusicFile);
//                clip.open(audioInputStreamer);
//                clip.start();
//                System.out.println("Now playing: " + currentMusicFile.getName());
//                clip.addLineListener(event -> {
//                    if (event.getType() == LineEvent.Type.STOP) {
//                        if (currentIndex < currentPlaylist.size() - 1) {
//                            currentIndex++;
//                            play();
//                        } else {
//                            System.out.println("End of playlist.");
//                            clip.close();
//                        }
//                    }
//                });
//            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private void stop() {
        if (clip.isRunning()) {
            clip.stop();
            clip.setFramePosition(0);
            System.out.println("Playback stopped.");
        }
    }

    private void next() {
        List<File> currentPlaylist = playlists.get(currentPlaylistName);
        if (currentPlaylist != null) {
            if (currentIndex < currentPlaylist.size() - 1) {
                currentIndex++;
                play();
            } else {
                System.out.println("No more songs in the playlist.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MusicStreamerWithPlaylists());
    }

}


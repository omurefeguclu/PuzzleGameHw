package com.omurefeguclu.puzzlegamehw;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.*;

public class SettingsManager {
    private final String SETTINGS_FILENAME = "puzzle-game-settings.txt";

    private static SettingsManager instance;

    public static SettingsManager getInstance() {
        if(instance == null){
            instance = new SettingsManager();
        }

        return instance;
    }

    private IntegerProperty musicVolumeProperty;
    public int getMusicVolume() {return musicVolumeProperty.get();}
    public IntegerProperty getMusicVolumeProperty()
    {
        return musicVolumeProperty;
    }
    public void setMusicVolume(int volume)
    {
        musicVolumeProperty.set(volume);
        SaveSettings();
    }

    private IntegerProperty sfxVolumeProperty;
    public int getSFXVolume() {return sfxVolumeProperty.get();}
    public IntegerProperty getSFXVolumeProperty()
    {
        return sfxVolumeProperty;
    }
    public void setSFXVolume(int volume)
    {
        sfxVolumeProperty.set(volume);
        SaveSettings();
    }


    private void SaveSettings()
    {
        try {
            FileWriter fileWriter = new FileWriter(SETTINGS_FILENAME);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.printf("music_volume:%d", musicVolumeProperty.get());
            printWriter.println();
            printWriter.printf("sfx_volume:%d", sfxVolumeProperty.get());

            printWriter.close();
        }
        catch(IOException exception) {
            exception.printStackTrace();
        }
    }
    private void ReadSettings()
    {
        File f = new File(SETTINGS_FILENAME);
        if (!f.exists()) {
            musicVolumeProperty = new SimpleIntegerProperty(100);
            sfxVolumeProperty = new SimpleIntegerProperty(100);

            SaveSettings();
        }
        else
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
                String line;
                if((line = reader.readLine()) != null)
                {
                    int musicVolume = Integer.parseInt(line.split(":")[1]);
                    musicVolumeProperty = new SimpleIntegerProperty(musicVolume);
                }
                if((line = reader.readLine()) != null)
                {
                    int sfxVolume = Integer.parseInt(line.split(":")[1]);
                    sfxVolumeProperty = new SimpleIntegerProperty(sfxVolume);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void Initialize(){
        ReadSettings();

    }
}

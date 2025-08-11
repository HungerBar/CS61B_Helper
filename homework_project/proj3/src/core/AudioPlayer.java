package core;

import javax.sound.sampled.*;

import javax.sound.sampled.*;

public class AudioPlayer {
    private static Clip backgroundClip;

    // 播放背景音乐（在单独线程中循环播放）
    public static void playBackgroundMusic(String resourcePath) {
        new Thread(() -> {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                        AudioPlayer.class.getResourceAsStream(resourcePath));

                AudioFormat format = audioStream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);

                backgroundClip = (Clip) AudioSystem.getLine(info);
                backgroundClip.open(audioStream);

                // 设置音量
                if (backgroundClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) backgroundClip.getControl(FloatControl.Type.MASTER_GAIN);
                    float dB = -10.0f;
                    // 确保在有效范围内
                    dB = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB));
                    gainControl.setValue(dB);
                }

                // 循环播放
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    // 停止背景音乐
    public static void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }
}
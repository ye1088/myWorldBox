package hlx.mcstorymode;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import com.MCWorld.framework.base.log.HLog;

/* compiled from: BgMusicOperator */
public class a {
    public static void a(Activity aty, MediaPlayer mediaPlayer) {
        try {
            AssetFileDescriptor _afd = aty.getAssets().openFd("calm2.mp3");
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            try {
                mediaPlayer2.setDataSource(_afd.getFileDescriptor(), _afd.getStartOffset(), _afd.getLength());
                mediaPlayer = mediaPlayer2;
            } catch (Exception e) {
                mediaPlayer = mediaPlayer2;
                HLog.verbose("Exception", "GET IT", new Object[0]);
            }
        } catch (Exception e2) {
            HLog.verbose("Exception", "GET IT", new Object[0]);
        }
    }

    public static void a(MediaPlayer mediaPlayer) {
        try {
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT,%s", new Object[]{e.toString()});
        }
    }
}

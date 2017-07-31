package com.mojang.minecraftpe.platforms;

import android.annotation.TargetApi;
import android.os.Handler;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;

@TargetApi(19)
/* compiled from: Platform19 */
public class b extends d {
    private Runnable bIF;
    private View bIG;
    private Handler bIH;

    public b(boolean paramBoolean) {
        if (paramBoolean) {
            this.bIH = new Handler();
        }
    }

    public void H(View paramView) {
        if (this.bIH != null) {
            this.bIG = paramView;
            this.bIG.setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener(this) {
                final /* synthetic */ b bII;

                {
                    this.bII = this$0;
                }

                public void onSystemUiVisibilityChange(int paramAnonymousInt) {
                    this.bII.bIH.postDelayed(this.bII.bIF, 500);
                }
            });
            this.bIF = new Runnable(this) {
                final /* synthetic */ b bII;

                {
                    this.bII = this$0;
                }

                public void run() {
                    this.bII.bIG.setSystemUiVisibility(5894);
                }
            };
            this.bIH.post(this.bIF);
        }
    }

    public void du(boolean paramBoolean) {
        if (this.bIH != null && paramBoolean) {
            this.bIH.postDelayed(this.bIF, 500);
        }
    }

    public void Ry() {
    }
}

package com.huluxia.mojang;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.mojang.converter.InventorySlot;
import com.huluxia.mojang.entity.DataConstants.ColorDataItem;
import com.huluxia.mojang.entity.EntityType;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.util.List;

public class MyActivity extends Activity {
    private static final String TAG = "test";
    TextView mResult;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Mojang.instance().init("My World", 0, null);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        setContentView(R.layout.test_leveldb);
        this.mResult = (TextView) findViewById(R.id.result);
        ((EditText) findViewById(R.id.count)).setVisibility(8);
        ((EditText) findViewById(R.id.count_inventory)).setVisibility(8);
        findViewById(R.id.read).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().readEntityData();
                    Log.d(MyActivity.TAG, "level  = " + Mojang.instance().getLevel());
                    MyActivity.this.mResult.append("内容：\n " + Mojang.instance().getLevel());
                } catch (Exception e) {
                    Log.e(MyActivity.this.toString(), "ex happens", e);
                }
            }
        });
        findViewById(R.id.write).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setEntity(EntityType.CHICKEN, 6);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.read_inventory).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    List<InventorySlot> data = Mojang.instance().readInventory();
                    Log.d(MyActivity.TAG, "Inventory内容  = " + data);
                    MyActivity.this.mResult.append("Inventory内容：\n " + data);
                } catch (Exception e) {
                    Log.e(MyActivity.this.toString(), "ex happens", e);
                }
            }
        });
        findViewById(R.id.write_invertory).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
            }
        });
        findViewById(R.id.delte).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().deleteEntity(EntityType.CHICKEN);
                } catch (Exception e) {
                    Log.e(MyActivity.this.toString(), "ex happens", e);
                }
            }
        });
        findViewById(R.id.delte_inventory).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().deleteInventory(new Integer(25).byteValue());
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.may_fly).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setMayFlying(true);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.not_may_fly).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setMayFlying(false);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.invulnerable).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setInvulnerable(true);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.not_invulnerable).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setInvulnerable(false);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.set_day).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setDay();
                    MyActivity.this.mResult.append(new Boolean(Mojang.instance().isDay()).toString() + SpecilApiUtil.LINE_SEP);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.set_night).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setNight();
                    MyActivity.this.mResult.append(new Boolean(Mojang.instance().isDay()).toString() + SpecilApiUtil.LINE_SEP);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.set_third_true).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setGameThirdPersionAngle(true);
                    MyActivity.this.mResult.append(new Boolean(Mojang.instance().isGameThirdPersionAngle()).toString() + SpecilApiUtil.LINE_SEP);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.set_third_false).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setGameThirdPersionAngle(false);
                    MyActivity.this.mResult.append(new Boolean(Mojang.instance().isGameThirdPersionAngle()).toString() + SpecilApiUtil.LINE_SEP);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.creative).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setGameMode(1);
                    MyActivity.this.mResult.append(String.valueOf(Mojang.instance().getGameMode()) + SpecilApiUtil.LINE_SEP);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.live).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setGameMode(0);
                    MyActivity.this.mResult.append(String.valueOf(Mojang.instance().getGameMode()) + SpecilApiUtil.LINE_SEP);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.growup).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().growup(EntityType.CHICKEN, true);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.not_grow).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().growup(EntityType.CHICKEN, false);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.paint_yellow).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().paintSheep(ColorDataItem.YELLOW);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.paint_orange).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().paintSheep(ColorDataItem.ORANGE);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.speed_1).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setSpeed(0.1f);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.speed_2).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().setSpeed(1.0f);
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
        findViewById(R.id.rename).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                MyActivity.kill(MyActivity.this);
                try {
                    Mojang.instance().renameGame(MyActivity.TAG + String.valueOf(SystemClock.currentThreadTimeMillis()));
                } catch (Exception e) {
                    Log.e(MyActivity.TAG, "write error ", e);
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Process.killProcess(Process.myPid());
    }

    private static String getDecode(short[] sArray) {
        char[] bArry = new char[(sArray.length - 1)];
        for (int n = 0; n < sArray.length - 1; n++) {
            bArry[n] = (char) (sArray[n + 1] ^ (n + 128));
        }
        return String.valueOf(bArry);
    }

    public static void kill(Context context) {
        ((ActivityManager) context.getSystemService("activity")).killBackgroundProcesses(getDecode(new short[]{(short) 0, (short) 227, (short) 238, (short) 239, (short) 173, (short) 233, (short) 234, (short) 236, (short) 230, (short) 230, (short) 238, (short) 164, (short) 230, (short) 229, (short) 227, (short) 235, (short) 236, (short) 226, (short) 240, (short) 244, (short) 231, (short) 228, (short) 240}));
    }
}

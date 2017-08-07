package hlx.mcspecialmode.firearms;

import com.huluxia.mcgame.h;
import com.huluxia.mcsdk.dtlib.c;
import com.tencent.mm.sdk.platformtools.FilePathGenerator;
import hlx.data.localstore.a;
import java.io.File;

/* compiled from: MCFirearmsFile13 */
public class b {
    private static final boolean DEBUG = false;
    private static int bUj = 81;
    private static String[][] bUk;
    private static b bUl;

    public static synchronized b Tq() {
        b bVar;
        synchronized (b.class) {
            if (bUl == null) {
                bUl = new b();
            }
            bVar = bUl;
        }
        return bVar;
    }

    public boolean hh_getIsRightMd5(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(a.bKU_more_sepack0130_md5)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean hi(String path) {
        String tmp_md5 = "";
        String std_md5 = "";
        String tmp_path = "";
        int i = 0;
        while (i < bUj) {
            try {
                tmp_path = path + File.separator + bUk[i][0];
                if (!c.getFileMD5(tmp_path).equalsIgnoreCase(bUk[i][1])) {
                    return false;
                }
                i++;
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    static {
        String[][] strArr = new String[81][];
        strArr[0] = new String[]{FilePathGenerator.NO_MEDIA_FILENAME, "d41d8cd98f00b204e9800998ecf8427e"};
        strArr[1] = new String[]{"44Magnum.mp3", "a9f2e1c5837180ef425f488da48a9f90"};
        strArr[2] = new String[]{"AA-12Shoot.ogg", "c7b0023f115e192ed81d7be6b8295581"};
        strArr[3] = new String[]{"AK47Shoot.ogg", "46d042091eceaff541db9d100efed072"};
        strArr[4] = new String[]{"AK74Shoot.ogg", "e624824f31743e39e2f61739b4e61e1d"};
        strArr[5] = new String[]{"AT4_and_M72LAW_and_Panzerfaust3Shoot.ogg", "183e4e47af9d69c7c67baf193bdbf035"};
        strArr[6] = new String[]{"BarrettShoot.ogg", "fec5076d4bef2d6489140931a0a50691"};
        strArr[7] = new String[]{"bell.mp3", "a242ef6ec9f998edfa3d4de55dbd4a65"};
        strArr[8] = new String[]{"benboncan_parachute.mp3", "6cd30311896d543a34b6f7035a832f0b"};
        strArr[9] = new String[]{"CrossbowShoot.wav", "4711ef20a5cb9b18c37265d9b15528ad"};
        strArr[10] = new String[]{"DesertEagleShoot.ogg", "5d6234d69f233f6fce06de7805524636"};
        strArr[11] = new String[]{"DragunovShoot.ogg", "cb3a2843b5513b3fc358cfd0f2a21375"};
        strArr[12] = new String[]{"EmptyGun.ogg", "3115142754d18558ab52954d3518df85"};
        strArr[13] = new String[]{"explosion-countdown.wav", "6d10bc576390eaf45da84576bf788fe"};
        strArr[14] = new String[]{"fire-explosion.mp3", "5122fac4bdcc591cdec950406d4b2ab4"};
        strArr[15] = new String[]{"flamethrower.mp3", "ce107faef3c236906f2879bc609bc705"};
        strArr[16] = new String[]{"FNSCAR_and_AUG_and_MTARShoot.ogg", "fcbdcb3f6117dda935829a14e1fb3943"};
        strArr[17] = new String[]{"G36Shoot.ogg", "5aa74c0440c3143c583430085e5cd33a"};
        strArr[18] = new String[]{"GlockShoot.ogg", "b399550697f8e6838a139e5770c66124"};
        strArr[19] = new String[]{"GrenadeLauncherShoot.ogg", "ee06ff58f068c5dff3e1f6e937e799c6"};
        strArr[20] = new String[]{"ignite_flamethrower1.ogg", "72cc54467d41fcf4ad384e08be1d738c"};
        strArr[21] = new String[]{"ignite_flamethrower2.ogg", "69bcbfedaae3849d5cf49053522f00a0"};
        strArr[22] = new String[]{"ignite_flamethrower3.ogg", "238dd8e1dd106b5ae58c0c87c9b5c567"};
        strArr[23] = new String[]{"knife_on_blocks.mp3", "11c49939db5d6ac58da615cf01722f42"};
        strArr[24] = new String[]{"knife_stab1.mp3", "b2cdc100cf53945bd85f759aa852eb13"};
        strArr[25] = new String[]{"knife_stab2.mp3", "39ffb7ddad2ed0239727ab905c8eac72"};
        strArr[26] = new String[]{"L96Shoot.ogg", "5d8eb47c1924293426d25aef63a78ea"};
        strArr[27] = new String[]{"LICENSE.txt", "b68d8b778e536062afe9579acc778a1"};
        strArr[28] = new String[]{"M1014Shoot.ogg", "42586603f5fd9c159176d510ba48e197"};
        strArr[29] = new String[]{"M14_and_M16A4Shoot.ogg", "8c379a7bbbb22845babb99e6b75996b9"};
        strArr[30] = new String[]{"M1887Shoot.ogg", "59362ef5ab3972c5c3f234082efcbc0a"};
        strArr[31] = new String[]{"M21Shoot.ogg", "9b29a682765bc1c063755576de568f5c"};
        strArr[32] = new String[]{"M249_and_L86Shoot.ogg", "36007cb962fcd9320d3a52ddc0100cdc"};
        strArr[33] = new String[]{"M9Shoot.ogg", "b2814ef0a94584ab4a3fe83aab0fbf2"};
        strArr[34] = new String[]{"MakarovShoot.ogg", "281650fecce75e0ea2c7e84dd94dd4f3"};
        strArr[35] = new String[]{"MinigunCooldown.ogg", "f4efa432c4c9ca1dec9492e9d96c9504"};
        strArr[36] = new String[]{"MinigunSpin.ogg", "958aa3c36758d3a050e0a9881db91848"};
        strArr[37] = new String[]{"MinigunWarmup.ogg", "35f3c2feea0cc0afa481050a737ea9ad"};
        strArr[38] = new String[]{"MiniUziShoot.ogg", "b098246717aa19ebbc675a96d46a9139"};
        strArr[39] = new String[]{"MolotovExplosion.mp3", "99ebf5f978a131cc4a97e6ffdecbccfe"};
        strArr[40] = new String[]{"MP5Shoot.ogg", "f57e73862349a97492e769962f42a54d"};
        strArr[41] = new String[]{"MSRShoot.mp3", "95a9c60212fb742cd475087a27095988"};
        strArr[42] = new String[]{"MultipleRocketLauncherShoot.mp3", "b84e50d66ba1c2a840b3464864e4b862"};
        strArr[43] = new String[]{"P90_and_Bizon_and_G3Shoot_and_Minigun.ogg", "fb3f55a8e276ad9a2060c8c9f2d78cb7"};
        strArr[44] = new String[]{"R700_and_M40A3Shoot.ogg", "1930e2b343436270e2355e7479487b23"};
        strArr[45] = new String[]{"R870Shoot.ogg", "33e74c9e4f5cbe866bc55c5da1dbd46c"};
        strArr[46] = new String[]{"reload/BARReload.ogg", "be27d786d14b37c5a8f354da86667388"};
        strArr[47] = new String[]{"reload/BazookaReload.ogg", "939fb3b7a55385fdef9e59c6b4b83e9d"};
        strArr[48] = new String[]{"reload/BrenReload.ogg", "3e47e97115973c77fa71c52e66ce1256"};
        strArr[49] = new String[]{"reload/BrowningReload.ogg", "b2a6636cdc20de267f41e2f097f412fb"};
        strArr[50] = new String[]{"reload/ColtReload.ogg", "711f7abbc0bbcd9f170d4ce434316395"};
        strArr[51] = new String[]{"reload/CrossbowReload.wav", "145731bbe328fc5a45bfb3c907c80877"};
        strArr[52] = new String[]{"reload/DP28Reload.ogg", "ccc410e5f146f1cc10ca9aa30ed94d2"};
        strArr[53] = new String[]{"reload/GL6Reload.ogg", "d3d41b86d9d5102658392119c40a76c"};
        strArr[54] = new String[]{"reload/GrenadeLauncherReload.ogg", "7faa62bceaf500208a4e4fc08b63f80d"};
        strArr[55] = new String[]{"reload/Kar98kReload.ogg", "ecc6277a44e217a96ee617ae81c1a717"};
        strArr[56] = new String[]{"reload/LeeEnfieldReload.ogg", "7a6cc3c4949f090b1138feec244ff389"};
        strArr[57] = new String[]{"reload/LugerReload.ogg", "d25bdb00689730cc667f808014cf17d9"};
        strArr[58] = new String[]{"reload/M1014Reload.ogg", "2440f960258037c073662bc5154c3957"};
        strArr[59] = new String[]{"reload/M1887Reload.ogg", "65a3d827552167b5e91c1d4c1eae12c4"};
        strArr[60] = new String[]{"reload/M1CarbineReload.ogg", "4211c9575de35cc2ee35141ff4ef3ee6"};
        strArr[61] = new String[]{"reload/MG42Reload.ogg", "468c35de1eb6efc8358110ce01caf4d"};
        strArr[62] = new String[]{"reload/MP40Reload.ogg", "7f6c3dd4b223ca3732251143db923f48"};
        strArr[63] = new String[]{"reload/MP44Reload.ogg", "1d96b427751340e6c4aa71b2e6e068d9"};
        strArr[64] = new String[]{"reload/PPSHReload.ogg", "e175c5d3bda9a2baf90f9d639b34952a"};
        strArr[65] = new String[]{"reload/Reload1.ogg", "a3dea49b0e45d093a4cfdd477e89ac2c"};
        strArr[66] = new String[]{"reload/SpringfieldReload.ogg", "31281540dae6cab90cfc4a7c3e0bd99e"};
        strArr[67] = new String[]{"reload/StenReload.ogg", "e2d6fd79d5149699873523b82776ee0f"};
        strArr[68] = new String[]{"reload/ThompsonReload.ogg", "e8291c0fb2fad403f3020c60f9a6eb67"};
        strArr[69] = new String[]{"reload/TT33Reload.ogg", "489e57e718862b855bd363d011d6f4ca"};
        strArr[70] = new String[]{"reload/W1200Reload.ogg", "3617bb501b3830af060f6ab079f1201d"};
        strArr[71] = new String[]{"riot_shield_attack.mp3", "85cf0b0d6e28f4772ad9a4fdce70f64e"};
        strArr[72] = new String[]{"RPD_and_M60E4_and_RPKShoot.ogg", "5464491b6127c937b5c72f37a3b96abe"};
        strArr[73] = new String[]{"RPGShoot.ogg", "a68c4ee1b21bc6e620077937ce8e2bbc"};
        strArr[74] = new String[]{"SG550Shoot.ogg", "1e545267f3c21f2ff93db6260e108425"};
        strArr[75] = new String[]{"SIGP226Shoot.ogg", "9bbba3a4b09669e7821c7b5862038357"};
        strArr[76] = new String[]{"SkorpionShoot.ogg", "4a1153a5dff5735c0b49babbaf487102"};
        strArr[77] = new String[]{"smoke-grenade.mp3", "8c120b6765fac9ba3e6405babc75d27c"};
        strArr[78] = new String[]{"USPShoot.ogg", "1582f6b1c7c2ef3e05deb7db6ed14e0d"};
        strArr[79] = new String[]{"version.txt", "b7b2e9d263ac5ad5f745451156bb97a6"};
        strArr[80] = new String[]{"W1200_and_SPASShoot.ogg", "8c94273d0d5c72c9f777fe0044914e41"};
        bUk = strArr;
    }
}

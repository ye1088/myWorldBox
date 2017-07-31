package hlx.mcspecialmode.firearms;

import com.huluxia.mcgame.h;
import com.huluxia.mcsdk.dtlib.c;
import java.io.File;

/* compiled from: MCFirearmsFile */
public class a {
    private static final boolean DEBUG = false;
    private static a bUi;
    private static int bUj = 79;
    private static String[][] bUk;

    public static synchronized a Tp() {
        a aVar;
        synchronized (a.class) {
            if (bUi == null) {
                bUi = new a();
            }
            aVar = bUi;
        }
        return aVar;
    }

    public boolean hh(String inputFileFullpath) {
        try {
            if (h.getFileMD5(new File(inputFileFullpath)).equalsIgnoreCase(hlx.data.localstore.a.bKR)) {
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
        r0 = new String[79][];
        r0[0] = new String[]{"44Magnum.mp3", "a9f2e1c5837180ef425f488da48a9f90"};
        r0[1] = new String[]{"AA-12Shoot.ogg", "c7b0023f115e192ed81d7be6b8295581"};
        r0[2] = new String[]{"AK47Shoot.ogg", "46d042091eceaff541db9d100efed072"};
        r0[3] = new String[]{"AK74Shoot.ogg", "e624824f31743e39e2f61739b4e61e1d"};
        r0[4] = new String[]{"AT4_and_M72LAW_and_Panzerfaust3Shoot.ogg", "183e4e47af9d69c7c67baf193bdbf035"};
        r0[5] = new String[]{"BarrettShoot.ogg", "46a56d1640e38acb783a18dd5aff3f83"};
        r0[6] = new String[]{"bell.mp3", "a242ef6ec9f998edfa3d4de55dbd4a65"};
        r0[7] = new String[]{"benboncan_parachute.mp3", "6cd30311896d543a34b6f7035a832f0b"};
        r0[8] = new String[]{"CrossbowShoot.wav", "4711ef20a5cb9b18c37265d9b15528ad"};
        r0[9] = new String[]{"DesertEagleShoot.ogg", "5d6234d69f233f6fce06de7805524636"};
        r0[10] = new String[]{"DragunovShoot.ogg", "cb3a2843b5513b3fc358cfd0f2a21375"};
        r0[11] = new String[]{"EmptyGun.ogg", "3115142754d18558ab52954d3518df85"};
        r0[12] = new String[]{"explosion-countdown.wav", "6d10bc576390eaf45da84576bf788fe"};
        r0[13] = new String[]{"fire-explosion.mp3", "5122fac4bdcc591cdec950406d4b2ab4"};
        r0[14] = new String[]{"flamethrower.mp3", "ce107faef3c236906f2879bc609bc705"};
        r0[15] = new String[]{"FNSCAR_and_AUG_and_MTARShoot.ogg", "fcbdcb3f6117dda935829a14e1fb3943"};
        r0[16] = new String[]{"G36Shoot.ogg", "5aa74c0440c3143c583430085e5cd33a"};
        r0[17] = new String[]{"GlockShoot.ogg", "b399550697f8e6838a139e5770c66124"};
        r0[18] = new String[]{"GrenadeLauncherShoot.ogg", "ee06ff58f068c5dff3e1f6e937e799c6"};
        r0[19] = new String[]{"ignite_flamethrower1.ogg", "72cc54467d41fcf4ad384e08be1d738c"};
        r0[20] = new String[]{"ignite_flamethrower2.ogg", "69bcbfedaae3849d5cf49053522f00a0"};
        r0[21] = new String[]{"ignite_flamethrower3.ogg", "238dd8e1dd106b5ae58c0c87c9b5c567"};
        r0[22] = new String[]{"knife_on_blocks.mp3", "11c49939db5d6ac58da615cf01722f42"};
        r0[23] = new String[]{"knife_stab1.mp3", "b2cdc100cf53945bd85f759aa852eb13"};
        r0[24] = new String[]{"knife_stab2.mp3", "39ffb7ddad2ed0239727ab905c8eac72"};
        r0[25] = new String[]{"L96Shoot.ogg", "5d8eb47c1924293426d25aef63a78ea"};
        r0[26] = new String[]{"LICENSE.txt", "b68d8b778e536062afe9579acc778a1"};
        r0[27] = new String[]{"M1014Shoot.ogg", "42586603f5fd9c159176d510ba48e197"};
        r0[28] = new String[]{"M14_and_M16A4Shoot.ogg", "8c379a7bbbb22845babb99e6b75996b9"};
        r0[29] = new String[]{"M1887Shoot.ogg", "59362ef5ab3972c5c3f234082efcbc0a"};
        r0[30] = new String[]{"M21Shoot.ogg", "9b29a682765bc1c063755576de568f5c"};
        r0[31] = new String[]{"M249_and_L86Shoot.ogg", "36007cb962fcd9320d3a52ddc0100cdc"};
        r0[32] = new String[]{"M9Shoot.ogg", "b2814ef0a94584ab4a3fe83aab0fbf2"};
        r0[33] = new String[]{"MakarovShoot.ogg", "281650fecce75e0ea2c7e84dd94dd4f3"};
        r0[34] = new String[]{"MinigunCooldown.ogg", "f4efa432c4c9ca1dec9492e9d96c9504"};
        r0[35] = new String[]{"MinigunSpin.ogg", "958aa3c36758d3a050e0a9881db91848"};
        r0[36] = new String[]{"MinigunWarmup.ogg", "35f3c2feea0cc0afa481050a737ea9ad"};
        r0[37] = new String[]{"MiniUziShoot.ogg", "b098246717aa19ebbc675a96d46a9139"};
        r0[38] = new String[]{"MolotovExplosion.mp3", "99ebf5f978a131cc4a97e6ffdecbccfe"};
        r0[39] = new String[]{"MP5Shoot.ogg", "f57e73862349a97492e769962f42a54d"};
        r0[40] = new String[]{"MSRShoot.mp3", "95a9c60212fb742cd475087a27095988"};
        r0[41] = new String[]{"P90_and_Bizon_and_G3Shoot_and_Minigun.ogg", "fb3f55a8e276ad9a2060c8c9f2d78cb7"};
        r0[42] = new String[]{"R700_and_M40A3Shoot.ogg", "1930e2b343436270e2355e7479487b23"};
        r0[43] = new String[]{"R870Shoot.ogg", "33e74c9e4f5cbe866bc55c5da1dbd46c"};
        r0[44] = new String[]{"riot_shield_attack.mp3", "85cf0b0d6e28f4772ad9a4fdce70f64e"};
        r0[45] = new String[]{"RPD_and_M60E4_and_RPKShoot.ogg", "5464491b6127c937b5c72f37a3b96abe"};
        r0[46] = new String[]{"RPGShoot.ogg", "a68c4ee1b21bc6e620077937ce8e2bbc"};
        r0[47] = new String[]{"SG550Shoot.ogg", "1e545267f3c21f2ff93db6260e108425"};
        r0[48] = new String[]{"SIGP226Shoot.ogg", "9bbba3a4b09669e7821c7b5862038357"};
        r0[49] = new String[]{"SkorpionShoot.ogg", "4a1153a5dff5735c0b49babbaf487102"};
        r0[50] = new String[]{"smoke-grenade.mp3", "8c120b6765fac9ba3e6405babc75d27c"};
        r0[51] = new String[]{"USPShoot.ogg", "1582f6b1c7c2ef3e05deb7db6ed14e0d"};
        r0[52] = new String[]{"version.txt", "dac89026a1d26bfff9a0f3ab4612b2da"};
        r0[53] = new String[]{"W1200_and_SPASShoot.ogg", "8c94273d0d5c72c9f777fe0044914e41"};
        r0[54] = new String[]{"reload/BARReload.ogg", "be27d786d14b37c5a8f354da86667388"};
        r0[55] = new String[]{"reload/BazookaReload.ogg", "939fb3b7a55385fdef9e59c6b4b83e9d"};
        r0[56] = new String[]{"reload/BrenReload.ogg", "3e47e97115973c77fa71c52e66ce1256"};
        r0[57] = new String[]{"reload/BrowningReload.ogg", "b2a6636cdc20de267f41e2f097f412fb"};
        r0[58] = new String[]{"reload/ColtReload.ogg", "711f7abbc0bbcd9f170d4ce434316395"};
        r0[59] = new String[]{"reload/CrossbowReload.wav", "145731bbe328fc5a45bfb3c907c80877"};
        r0[60] = new String[]{"reload/DP28Reload.ogg", "ccc410e5f146f1cc10ca9aa30ed94d2"};
        r0[61] = new String[]{"reload/GL6Reload.ogg", "d3d41b86d9d5102658392119c40a76c"};
        r0[62] = new String[]{"reload/GrenadeLauncherReload.ogg", "7faa62bceaf500208a4e4fc08b63f80d"};
        r0[63] = new String[]{"reload/Kar98kReload.ogg", "ecc6277a44e217a96ee617ae81c1a717"};
        r0[64] = new String[]{"reload/LeeEnfieldReload.ogg", "7a6cc3c4949f090b1138feec244ff389"};
        r0[65] = new String[]{"reload/LugerReload.ogg", "d25bdb00689730cc667f808014cf17d9"};
        r0[66] = new String[]{"reload/M1014Reload.ogg", "2440f960258037c073662bc5154c3957"};
        r0[67] = new String[]{"reload/M1887Reload.ogg", "65a3d827552167b5e91c1d4c1eae12c4"};
        r0[68] = new String[]{"reload/M1CarbineReload.ogg", "4211c9575de35cc2ee35141ff4ef3ee6"};
        r0[69] = new String[]{"reload/MG42Reload.ogg", "468c35de1eb6efc8358110ce01caf4d"};
        r0[70] = new String[]{"reload/MP40Reload.ogg", "7f6c3dd4b223ca3732251143db923f48"};
        r0[71] = new String[]{"reload/MP44Reload.ogg", "1d96b427751340e6c4aa71b2e6e068d9"};
        r0[72] = new String[]{"reload/PPSHReload.ogg", "e175c5d3bda9a2baf90f9d639b34952a"};
        r0[73] = new String[]{"reload/Reload1.ogg", "a3dea49b0e45d093a4cfdd477e89ac2c"};
        r0[74] = new String[]{"reload/SpringfieldReload.ogg", "31281540dae6cab90cfc4a7c3e0bd99e"};
        r0[75] = new String[]{"reload/StenReload.ogg", "e2d6fd79d5149699873523b82776ee0f"};
        r0[76] = new String[]{"reload/ThompsonReload.ogg", "e8291c0fb2fad403f3020c60f9a6eb67"};
        r0[77] = new String[]{"reload/TT33Reload.ogg", "489e57e718862b855bd363d011d6f4ca"};
        r0[78] = new String[]{"reload/W1200Reload.ogg", "3617bb501b3830af060f6ab079f1201d"};
        bUk = r0;
    }
}

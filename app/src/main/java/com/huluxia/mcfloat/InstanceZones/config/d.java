package com.huluxia.mcfloat.InstanceZones.config;

import com.huluxia.framework.base.log.HLog;
import com.huluxia.mcfb.b;
import com.huluxia.mcfloat.InstanceZones.structrue.a;
import com.huluxia.mcfloat.InstanceZones.structrue.c;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/* compiled from: InsZonesLoadConfig */
public class d {
    private InputStream y(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);
            inputStream.close();
            for (int i = 0; i < b.length; i++) {
                b[i] = (byte) (b[i] ^ (i + 9001));
            }
            return new ByteArrayInputStream(b);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
            return null;
        }
    }

    public b a(String insZonesType, InputStream inputStream, boolean isdecode) {
        IOException e;
        Throwable th;
        if (isdecode) {
            inputStream = y(inputStream);
        }
        if (inputStream == null) {
            return null;
        }
        BufferedReader reader = null;
        b _InsZoneAllPara = new b();
        String _singleParaStr = "default--Top";
        try {
            c _InsZoneCommPara;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int line = 1;
            c _InsZoneCommPara2 = null;
            while (true) {
                try {
                    String tempString = bufferedReader.readLine();
                    if (tempString == null) {
                        break;
                    }
                    String[] sourceStrArray = tempString.split("#");
                    if (sourceStrArray[0].equals(insZonesType)) {
                        if (_singleParaStr.equals(sourceStrArray[1])) {
                            _InsZoneCommPara = _InsZoneCommPara2;
                        } else {
                            _singleParaStr = sourceStrArray[1];
                            _InsZoneCommPara = new c();
                            try {
                                _InsZoneAllPara.b(_InsZoneCommPara);
                            } catch (IOException e2) {
                                e = e2;
                                reader = bufferedReader;
                            } catch (Throwable th2) {
                                th = th2;
                                reader = bufferedReader;
                            }
                        }
                        if (sourceStrArray[2].equals("Tips")) {
                            a(_InsZoneCommPara, sourceStrArray[3], sourceStrArray[4]);
                        } else if (sourceStrArray[2].equals("GameMonster")) {
                            a(_InsZoneCommPara, sourceStrArray[3], sourceStrArray[4], sourceStrArray[5], sourceStrArray[6], sourceStrArray[8], sourceStrArray[9].equals("InitBlood") ? sourceStrArray[10] : null);
                        } else if (sourceStrArray[2].equals("WaitTime")) {
                            c(_InsZoneCommPara, sourceStrArray[3]);
                        } else if (sourceStrArray[2].equals("ChallengeTime")) {
                            b(_InsZoneCommPara, sourceStrArray[3]);
                        } else if (sourceStrArray[2].equals("EnvironmentWL")) {
                            b(_InsZoneCommPara, sourceStrArray[3], sourceStrArray[4]);
                        } else if (sourceStrArray[2].equals("EnvironmentEllipseAxis")) {
                            c(_InsZoneCommPara, sourceStrArray[3], sourceStrArray[4]);
                        } else if (sourceStrArray[2].equals("Height")) {
                            a(_InsZoneCommPara, sourceStrArray[3]);
                        } else if (sourceStrArray[2].equals("BloodScore") && sourceStrArray[3].equals("Self")) {
                            d(_InsZoneCommPara, sourceStrArray[5]);
                        } else if (sourceStrArray[2].equals("SelfMaxBloodValue")) {
                            e(_InsZoneCommPara, sourceStrArray[3]);
                        } else if (sourceStrArray[2].equals("EnchantEquipment")) {
                            a(_InsZoneCommPara, sourceStrArray[3], sourceStrArray[4], sourceStrArray[6]);
                        } else if (sourceStrArray[2].equals("WearEquipment")) {
                            d(_InsZoneCommPara, sourceStrArray[4], sourceStrArray[5]);
                        } else if (sourceStrArray[2].equals("BagItem")) {
                            b(_InsZoneCommPara, sourceStrArray[4], sourceStrArray[5], sourceStrArray[7]);
                        } else if (sourceStrArray[2].equals("AnimalScore")) {
                            e(_InsZoneCommPara, sourceStrArray[4], sourceStrArray[5]);
                        } else if (sourceStrArray[2].equals("SafeCoordinate")) {
                            com.huluxia.mcfloat.InstanceZones.structrue.d _routeBlock = new com.huluxia.mcfloat.InstanceZones.structrue.d();
                            a(_routeBlock, sourceStrArray[3]);
                            a(_routeBlock, sourceStrArray[5], sourceStrArray[6]);
                            _InsZoneCommPara.a(_routeBlock);
                        }
                        line++;
                        _InsZoneCommPara2 = _InsZoneCommPara;
                    }
                } catch (IOException e3) {
                    e = e3;
                    _InsZoneCommPara = _InsZoneCommPara2;
                    reader = bufferedReader;
                } catch (Throwable th3) {
                    th = th3;
                    _InsZoneCommPara = _InsZoneCommPara2;
                    reader = bufferedReader;
                }
            }
            bufferedReader.close();
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    _InsZoneCommPara = _InsZoneCommPara2;
                    reader = bufferedReader;
                    return _InsZoneAllPara;
                } catch (IOException e4) {
                    _InsZoneCommPara = _InsZoneCommPara2;
                    reader = bufferedReader;
                    return _InsZoneAllPara;
                }
            }
            reader = bufferedReader;
            return _InsZoneAllPara;
        } catch (IOException e5) {
            e = e5;
            try {
                e.printStackTrace();
                if (reader == null) {
                    return _InsZoneAllPara;
                }
                try {
                    reader.close();
                    return _InsZoneAllPara;
                } catch (IOException e6) {
                    return _InsZoneAllPara;
                }
            } catch (Throwable th4) {
                th = th4;
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e7) {
                    }
                }
                throw th;
            }
        }
    }

    private void a(com.huluxia.mcfloat.InstanceZones.structrue.d routeBlock, String objectStr) {
        int abscissa = 0;
        int ordinate = 0;
        try {
            abscissa = Integer.parseInt(objectStr.substring(0, objectStr.indexOf(44)));
            ordinate = Integer.parseInt(objectStr.substring(objectStr.indexOf(44) + 1));
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        routeBlock.fs(abscissa);
        routeBlock.ft(ordinate);
    }

    private void a(com.huluxia.mcfloat.InstanceZones.structrue.d routeBlock, String blockId, String blockDmg) {
        int id = 0;
        int dmg = 0;
        try {
            id = Integer.parseInt(blockId);
            dmg = Integer.parseInt(blockDmg);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        routeBlock.setBlockId(id);
        routeBlock.fu(dmg);
    }

    private void a(c singlePara, String strTime, String strTips) {
        int time = 0;
        try {
            time = Integer.parseInt(strTime);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        singlePara.a(new a(time, strTips));
    }

    private void a(c singlePara, String inputID, String inputDmg, String inputCount, String inputCorner, String occurrenceTime, String initBlood) {
        try {
            int _tmpID = Integer.parseInt(inputID);
            int _tmpDmg = Integer.parseInt(inputDmg);
            int _tmpCount = Integer.parseInt(inputCount);
            int _tmpCorner = Integer.parseInt(inputCorner);
            int _tmpTime = Integer.parseInt(occurrenceTime);
            int _tmpBloodValue = 0;
            if (initBlood != null) {
                _tmpBloodValue = Integer.parseInt(initBlood);
            }
            b mcfbitem = new b(1, _tmpID, _tmpDmg, _tmpCount, _tmpCorner, _tmpBloodValue);
            ArrayList<b> _monsterList;
            if (singlePara.tA() != null) {
                c _tmp = (c) singlePara.tA().get(singlePara.tA().size() - 1);
                if (_tmp.Zr == _tmpTime) {
                    _tmp.Zq.add(mcfbitem);
                    return;
                }
                _monsterList = new ArrayList();
                _monsterList.add(mcfbitem);
                singlePara.a(new c(_tmpTime, _monsterList));
                return;
            }
            _monsterList = new ArrayList();
            _monsterList.add(mcfbitem);
            singlePara.a(new c(_tmpTime, _monsterList));
        } catch (Exception e) {
        }
    }

    private void b(c singlePara, String strWidth, String strLength) {
        int i;
        int i2 = 0;
        int width = 0;
        int length = 0;
        try {
            width = Integer.parseInt(strWidth);
            length = Integer.parseInt(strLength);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        if (width % 2 == 0) {
            i = 0;
        } else {
            i = 1;
        }
        width += i;
        if (length % 2 != 0) {
            i2 = 1;
        }
        length += i2;
        singlePara.setWidth(width);
        singlePara.setLength(length);
    }

    private void c(c singlePara, String strMajorAxis, String strMinorAxis) {
        int i;
        int i2 = 0;
        int majorAxis = 0;
        int minorAxis = 0;
        try {
            majorAxis = Integer.parseInt(strMajorAxis);
            minorAxis = Integer.parseInt(strMinorAxis);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        if (majorAxis % 2 == 0) {
            i = 0;
        } else {
            i = 1;
        }
        majorAxis += i;
        if (minorAxis % 2 != 0) {
            i2 = 1;
        }
        minorAxis += i2;
        singlePara.eK(majorAxis);
        singlePara.eL(minorAxis);
    }

    private void a(c singlePara, String strHigh) {
        int high = 0;
        try {
            high = Integer.parseInt(strHigh);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        singlePara.eM(high);
    }

    private void b(c singlePara, String strChalengeTime) {
        int chalengeTime = 0;
        try {
            chalengeTime = Integer.parseInt(strChalengeTime);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        singlePara.eO(chalengeTime);
    }

    private void c(c singlePara, String strWaitTime) {
        int chalengeTime = 0;
        try {
            chalengeTime = Integer.parseInt(strWaitTime);
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
        singlePara.eN(chalengeTime);
    }

    private void d(c singlePara, String inputScore) {
        try {
            singlePara.eP(Integer.parseInt(inputScore));
        } catch (Exception e) {
        }
    }

    private void e(c singlePara, String inputScore) {
        try {
            singlePara.eQ(Integer.parseInt(inputScore));
        } catch (Exception e) {
        }
    }

    private void a(c singlePara, String inputID, String inputDmg, String inputCount) {
        try {
            singlePara.a(new b(0, Integer.parseInt(inputID), Integer.parseInt(inputDmg), Integer.parseInt(inputCount), 0));
        } catch (Exception e) {
            HLog.verbose("Exception", "GET IT!", new Object[0]);
        }
    }

    private void d(c singlePara, String inputID, String inputDmg) {
        try {
            singlePara.d(new b(3, Integer.parseInt(inputID), Integer.parseInt(inputDmg), 1, 6));
        } catch (Exception e) {
        }
    }

    private void b(c singlePara, String inputID, String inputDmg, String inputCount) {
        try {
            singlePara.c(new b(2, Integer.parseInt(inputID), Integer.parseInt(inputDmg), Integer.parseInt(inputCount), 5));
        } catch (Exception e) {
        }
    }

    private void e(c singlePara, String inputAnimalID, String inputAnimalScore) {
        try {
            int _tmpAnimalId = Integer.parseInt(inputAnimalID);
            int _tmpAnimalScore = Integer.parseInt(inputAnimalScore);
            switch (_tmpAnimalId) {
                case 10:
                    singlePara.eT(_tmpAnimalScore);
                    return;
                case 11:
                    singlePara.eU(_tmpAnimalScore);
                    return;
                case 12:
                    singlePara.eV(_tmpAnimalScore);
                    return;
                case 13:
                    singlePara.eW(_tmpAnimalScore);
                    return;
                case 14:
                    singlePara.eX(_tmpAnimalScore);
                    return;
                case 15:
                    singlePara.eY(_tmpAnimalScore);
                    return;
                case 16:
                    singlePara.eZ(_tmpAnimalScore);
                    return;
                case 17:
                    singlePara.fk(_tmpAnimalScore);
                    return;
                case 19:
                    singlePara.eR(_tmpAnimalScore);
                    return;
                case 20:
                    singlePara.fl(_tmpAnimalScore);
                    return;
                case 21:
                    singlePara.fm(_tmpAnimalScore);
                    return;
                case 22:
                    singlePara.fn(_tmpAnimalScore);
                    return;
                case 32:
                    singlePara.fa(_tmpAnimalScore);
                    return;
                case 33:
                    singlePara.fb(_tmpAnimalScore);
                    return;
                case 34:
                    singlePara.fc(_tmpAnimalScore);
                    return;
                case 35:
                    singlePara.fe(_tmpAnimalScore);
                    return;
                case 36:
                    singlePara.ff(_tmpAnimalScore);
                    return;
                case 37:
                    singlePara.fg(_tmpAnimalScore);
                    return;
                case 38:
                    singlePara.fh(_tmpAnimalScore);
                    return;
                case 39:
                    singlePara.fi(_tmpAnimalScore);
                    return;
                case 40:
                    singlePara.fo(_tmpAnimalScore);
                    return;
                case 41:
                    singlePara.fj(_tmpAnimalScore);
                    return;
                case 42:
                    singlePara.fp(_tmpAnimalScore);
                    return;
                case 43:
                    singlePara.eS(_tmpAnimalScore);
                    return;
                case 44:
                    singlePara.fq(_tmpAnimalScore);
                    return;
                default:
                    return;
            }
        } catch (Exception e) {
        }
    }
}

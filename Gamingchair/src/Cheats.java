public class Cheats extends Thread {
    public boolean isGlowEnabled() {
        return glowEnabled;
    }
    public void setGlowEnabled(boolean glowEnabled) {
        this.glowEnabled = glowEnabled;
    }
    private boolean glowEnabled = false;
    public boolean isThirdEnabled() {
        return thirdEnabled;
    }
    public void setThirdEnabled(boolean thirdEnabled) {
        this.thirdEnabled = thirdEnabled;
    }

    private boolean thirdEnabled = false;
    private int GlowManager;
    private int LocalPlayer;
    private final Util util = new Util();


    private void ThirdPerson() {
        if (isThirdEnabled())
            util.getProc().writeInt(LocalPlayer + Offset.m_iObserverMode, 1);
        else
            util.getProc().writeInt(LocalPlayer + Offset.m_iObserverMode, 0);
    }
    private void GlowESP(int curEnt) {
        int health = util.getProc().readInt(curEnt + Offset.m_iHealth);

        boolean dormant = util.getProc().readBoolean(curEnt + Offset.m_bDormant);

        int glowIndex;
        glowIndex = util.getProc().readInt(curEnt + Offset.m_iGlowIndex);
        int enemyTeam = util.getProc().readInt(curEnt + Offset.m_iTeamNum);
        int localTeam = util.getProc().readInt(LocalPlayer + Offset.m_iTeamNum);


        if (health > 0 && health < 101 && !dormant && isGlowEnabled()) {
            if (localTeam == enemyTeam) {
                util.getProc().writeFloat(GlowManager + glowIndex * 0x38 + 0x4, 0.f);//r
                util.getProc().writeFloat(GlowManager + glowIndex * 0x38 + 0x8, 1.f);//g
                util.getProc().writeFloat(GlowManager + glowIndex * 0x38 + 0xC, 0.f);//b
            } else {
                util.getProc().writeFloat(GlowManager + glowIndex * 0x38 + 0x4, 1.f);//r
                util.getProc().writeFloat(GlowManager + glowIndex * 0x38 + 0x8, 0.f);//g
                util.getProc().writeFloat(GlowManager + glowIndex * 0x38 + 0xC, 0.f);//b
            }
            util.getProc().writeFloat(GlowManager + glowIndex * 0x38 + 0x10, 1.f);//a
            util.getProc().writeBoolean(GlowManager + glowIndex * 0x38 + 0x24, true);//occluded
        }

    }
    private void EntityListLoop() {
        int[] Entity = new int[32];
        for(int i = 0; i < 32; i++){
            int curEnt = util.getProc().readInt(util.getClient().address() + Offset.dwEntityList + (i * 0x10));
            if (curEnt > 0) {
                Entity[i] = curEnt;
                GlowESP(Entity[i]);

            }
        }
    }

    @Override
    public void run() {
        super.run();
        final int STATE_IN_GAME = 6;
        do {
            int ClientState = util.getProc().readInt((util.getEngine().address() + Offset.dwClientState));
            int STATE = util.getProc().readInt(ClientState + Offset.dwClientState_State);
            GlowManager = util.getProc().readInt(util.getClient().address() + Offset.dwGlowObjectManager);
            if (STATE == STATE_IN_GAME) {
                LocalPlayer = util.getProc().readInt(util.getClient().address() + Offset.dwLocalPlayer);
                EntityListLoop();
                ThirdPerson();


            } else
                System.out.println("Esperando a entrar a una partida");


        } while (true);
    }

}

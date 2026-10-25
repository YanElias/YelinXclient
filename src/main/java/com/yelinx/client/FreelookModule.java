package com.yelinx.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

/**
 * Hold V to look around independently from the player's body.
 * Enable/disable this module in the YelinX ClickGUI first.
 */
public class FreelookModule extends ClientModule {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static FreelookCamera camera;

    public FreelookModule() {
        super("freelook", "Freelook", "Segure V para olhar ao redor sem girar o corpo.", "PLAYER");
    }

    public static void update() {
        EntityPlayerSP player = MC.thePlayer;
        if (player == null || MC.theWorld == null) {
            camera = null;
            return;
        }

        boolean shouldLook = ModuleManager.isEnabled("freelook")
                && Keyboard.isKeyDown(Keyboard.KEY_V)
                && MC.currentScreen == null;

        if (shouldLook) {
            if (camera == null || camera.worldObj != MC.theWorld) {
                camera = new FreelookCamera(MC.theWorld, player);
            }
            if (MC.getRenderViewEntity() != camera) {
                camera.copyRotationFrom(player);
                MC.setRenderViewEntity(camera);
            }
            camera.follow(player);
        } else if (MC.getRenderViewEntity() == camera) {
            MC.setRenderViewEntity(player);
            camera = null;
        }
    }

    private static class FreelookCamera extends Entity {
        protected FreelookCamera(World world, EntityPlayerSP player) {
            super(world);
            setSize(0.0F, 0.0F);
            noClip = true;
            copyRotationFrom(player);
            follow(player);
        }

        void copyRotationFrom(EntityPlayerSP player) {
            rotationYaw = player.rotationYaw;
            rotationPitch = player.rotationPitch;
            prevRotationYaw = rotationYaw;
            prevRotationPitch = rotationPitch;
        }

        void follow(EntityPlayerSP player) {
            setPosition(player.posX, player.posY, player.posZ);
            motionX = motionY = motionZ = 0.0D;
        }

        @Override
        public float getEyeHeight() {
            return MC.thePlayer == null ? 0.0F : MC.thePlayer.getEyeHeight();
        }

        @Override
        protected void entityInit() { }

        @Override
        protected void readEntityFromNBT(NBTTagCompound compound) { }

        @Override
        protected void writeEntityToNBT(NBTTagCompound compound) { }
    }
}

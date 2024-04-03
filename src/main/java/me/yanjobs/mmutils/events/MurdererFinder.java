package me.yanjobs.mmutils.events;

import me.yanjobs.mmutils.MMUtils;
import me.yanjobs.mmutils.utils.chat.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.weavemc.loader.api.event.RenderWorldEvent;
import net.weavemc.loader.api.event.SubscribeEvent;
import net.weavemc.loader.api.event.TickEvent;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import me.yanjobs.mmutils.utils.mm.KnifeSkins;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MurdererFinder {
    public static ArrayList<String> murderers;
    public static ArrayList<String> detectives;
    private World lastWorld;
    static boolean murderFound;

    public MurdererFinder() {
        this.lastWorld = null;
    }

    @SubscribeEvent
    public void renderMurdererHitbox(RenderWorldEvent event) throws IOException {
        if (!MMUtils.isInMMClassic) return;
        if (!Boolean.parseBoolean(MMUtils.getConfig().getProperty("enabled"))) return;;
        final List<String> playerList = getOnlinePlayersByName();
        for (String s : playerList) {
            final EntityPlayer player = Minecraft.getMinecraft().theWorld.getPlayerEntityByName((String) s);
            if (player != null && player != Minecraft.getMinecraft().thePlayer) {
                GlStateManager.disableDepth();
                renderHitBox((Entity) player, new Color(255, 255, 255), (float) (10 / Math.sqrt(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(player))), event.getPartialTicks());
                GlStateManager.enableDepth();

            }

            // Murderer
            if (player != null && player.getHeldItem() != null && player.getHeldItem().getItem() != null && KnifeSkins.isItemKnifeSkin(player.getHeldItem().getItem())) {
                boolean isInList = false;
                for (int x = 0; x < MurdererFinder.murderers.size(); ++x) {
                    if (MurdererFinder.murderers.get(x) == player.getName() && MurdererFinder.murderers.size() > 0) {
                        isInList = true;
                        break;
                    }
                }
                if (!isInList && player.getName() != Minecraft.getMinecraft().thePlayer.getName()) {
                    MurdererFinder.murderers.add(player.getName());
                    Message.sendMessage(player.getName(), Message.LEVEL.Murderer);
                }
            }
            for (int y = 0; y < MurdererFinder.murderers.size(); ++y) {
                final EntityPlayer murderer = Minecraft.getMinecraft().theWorld.getPlayerEntityByName((String) MurdererFinder.murderers.get(y));
                if (murderer != null) {
                    GlStateManager.disableDepth();
                    renderHitBox((Entity) murderer, new Color(255, 55, 55), (float) (10/Math.sqrt(Minecraft.getMinecraft().thePlayer.getDistanceToEntity(murderer))), event.getPartialTicks());

                    GlStateManager.enableDepth();
                }
            }

            // Detectives
            if (player != null && player.getHeldItem() != null && player.getHeldItem().getItem() != null && player.getHeldItem().getItem() == Items.bow) {
                boolean isInList = false;
                for (int x = 0; x < MurdererFinder.detectives.size(); ++x) {
                    if (MurdererFinder.detectives.get(x) == player.getName() && MurdererFinder.detectives.size() > 0) {
                        isInList = true;
                        break;
                    }
                }
                if (!isInList && player.getName() != Minecraft.getMinecraft().thePlayer.getName()) {
                    MurdererFinder.detectives.add(player.getName());
                    Message.sendMessage(player.getName(), Message.LEVEL.Detective);
                }
            }
            for (int y = 0; y < MurdererFinder.detectives.size(); ++y) {
                final EntityPlayer detective = Minecraft.getMinecraft().theWorld.getPlayerEntityByName((String) MurdererFinder.detectives.get(y));
                if (detective != null) {
                    GlStateManager.disableDepth();
                    renderHitBox((Entity) detective, new Color(0, 255, 255), (float) (10/Minecraft.getMinecraft().thePlayer.getDistanceToEntity(detective)), event.getPartialTicks());
                    GlStateManager.enableDepth();
                }
            }
        }

        // Gold?
        final List<Entity> entityList = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
        for (Entity e : entityList) {
            if (e instanceof EntityItem && ((EntityItem) e).getEntityItem().getItem() == Items.gold_ingot) {
                GlStateManager.disableDepth();
                renderHitBox(e, new Color(238, 188, 29), (float) (10 / Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), event.getPartialTicks()) ;
                GlStateManager.enableDepth();
            }
        }
    }

    @SubscribeEvent
    public void clearMurderers(final TickEvent event) {
        final World world = (World) Minecraft.getMinecraft().theWorld;
        if (world != null && world != this.lastWorld) {
            MurdererFinder.murderers.clear();
            MurdererFinder.detectives.clear();
            MMUtils.isInMMClassic = false;
            murderFound = false;
        }
        this.lastWorld = world;
    }

    static {
        MurdererFinder.murderers = new ArrayList<String>();
        MurdererFinder.detectives = new ArrayList<String>();
    }
    public static List<String> getOnlinePlayersByName() {
        final ArrayList<String> players = new ArrayList<String>();
        final Collection<NetworkPlayerInfo> playerCollection = (Collection<NetworkPlayerInfo>)Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
        for (final NetworkPlayerInfo networkPlayerInfo : playerCollection) {
            final String playerName = networkPlayerInfo.getGameProfile().getName();
            if (playerName != null) {
                players.add(playerName);
            }
        }
        return players;
    }
    public static void renderHitBox(final Entity entityIn, final Color color, final float lineWidth, final float partialTicks) {
        if (entityIn.ticksExisted == 0) {
            entityIn.lastTickPosX = entityIn.posX;
            entityIn.lastTickPosY = entityIn.posY;
            entityIn.lastTickPosZ = entityIn.posZ;
        }
        final double x = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        final double y = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        final double z = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GL11.glLineWidth(lineWidth);
        if (entityIn instanceof EntityPlayer) {
            final AxisAlignedBB axisalignedbb = new AxisAlignedBB(x - 0.5, y, z - 0.5, x + 0.5, y + entityIn.height, z + 0.5);
            RenderGlobal.drawOutlinedBoundingBox(axisalignedbb, color.getRed(), color.getGreen(), color.getBlue(), 255);
        }
        else {
            final float width = entityIn.width / 2.0f;
            final AxisAlignedBB axisalignedbb2 = new AxisAlignedBB(x - width, y, z - width, x + width, y + entityIn.height, z + width);
            RenderGlobal.drawOutlinedBoundingBox(axisalignedbb2, color.getRed(), color.getGreen(), color.getBlue(), 255);
        }
        GlStateManager.enableTexture2D();
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

}

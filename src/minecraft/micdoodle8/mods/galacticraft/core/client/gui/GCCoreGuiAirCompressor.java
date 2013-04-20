package micdoodle8.mods.galacticraft.core.client.gui;

import mekanism.api.EnumColor;
import micdoodle8.mods.galacticraft.core.inventory.GCCoreContainerAirCompressor;
import micdoodle8.mods.galacticraft.core.tile.GCCoreTileEntityOxygenCompressor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;

/**
 * Copyright 2012-2013, micdoodle8
 *
 *  All rights reserved.
 *
 */
public class GCCoreGuiAirCompressor extends GuiContainer
{
    private final GCCoreTileEntityOxygenCompressor compressorInv;

	public GCCoreGuiAirCompressor(InventoryPlayer par1InventoryPlayer, GCCoreTileEntityOxygenCompressor par2TileEntityAirDistributor)
	{
        super(new GCCoreContainerAirCompressor(par1InventoryPlayer, par2TileEntityAirDistributor));
        this.compressorInv = par2TileEntityAirDistributor;
        this.ySize = 180;
	}

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRenderer.drawString("Oxygen Compressor", 8, 10, 4210752);
        this.fontRenderer.drawString("In:", 90, 31, 4210752);
        String status = "Status: " + this.getStatus();
        this.fontRenderer.drawString(status, this.xSize / 2 - this.fontRenderer.getStringWidth(status) / 2, 50, 4210752);
        status = "Oxygen: " + this.compressorInv.getPower();
        this.fontRenderer.drawString(status, this.xSize / 2 - this.fontRenderer.getStringWidth(status) / 2, 60, 4210752);
        status = ElectricityDisplay.getDisplay(this.compressorInv.ueWattsPerTick * 20, ElectricUnit.WATT);
        this.fontRenderer.drawString(status, this.xSize / 2 - this.fontRenderer.getStringWidth(status) / 2, 70, 4210752);
        status = ElectricityDisplay.getDisplay(this.compressorInv.getVoltage(), ElectricUnit.VOLTAGE);
        this.fontRenderer.drawString(status, this.xSize / 2 - this.fontRenderer.getStringWidth(status) / 2, 80, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 104 + 17, 4210752);
    }

    private String getStatus()
    {
    	if (this.compressorInv.getStackInSlot(0) == null)
    	{
    		return EnumColor.DARK_RED + "No Valid Oxygen Tank";
    	}

    	if (this.compressorInv.getStackInSlot(0) != null && this.compressorInv.getStackInSlot(0).getItemDamage() == 0)
    	{
    		return EnumColor.DARK_RED + "Oxygen Tank Full";
    	}

    	if (this.compressorInv.wattsReceived == 0)
    	{
    		return EnumColor.DARK_RED + "Not Enough Power";
    	}

    	if (this.compressorInv.getPower() < 1)
    	{
    		return EnumColor.DARK_RED + "Not Enough Oxygen";
    	}

		return EnumColor.DARK_GREEN + "Active";
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture("/micdoodle8/mods/galacticraft/core/client/gui/compressor.png");
		final int var5 = (this.width - this.xSize) / 2;
		final int var6 = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(var5, var6 + 5, 0, 0, this.xSize, 181);

		if (this.compressorInv != null)
		{
			final int scale = (int) ((double) this.compressorInv.getPower() / (double) 31 * 54);
			this.drawTexturedModalRect(var5 + 108, var6 + 26, 176, 0, Math.min(scale, 54), 16);
		}
	}
}
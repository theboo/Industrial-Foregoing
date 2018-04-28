package com.buuz135.industrial.tile.block;

import com.buuz135.industrial.tile.WorkingAreaElectricMachine;
import com.buuz135.industrial.utils.WorkUtils;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public abstract class CustomAreaOrientedBlock<T extends WorkingAreaElectricMachine> extends CustomOrientedBlock {

    private int maxWidth;
    private int height;
    private boolean acceptsRangeAddon;
    private RangeType type;

    public CustomAreaOrientedBlock(String registryName, Class<T> teClass, Material material, int energyForWork, int energyRate, RangeType type, int maxWidth, int height, boolean acceptsRangeAddon) {
        super(registryName, teClass, material, energyForWork, energyRate);
        this.maxWidth = maxWidth;
        this.height = height;
        this.acceptsRangeAddon = acceptsRangeAddon;
        this.type = type;
    }


    public int getMaxWidth() {
        return maxWidth;
    }

    public int getHeight() {
        return height;
    }

    public RangeType getType() {
        return type;
    }

    public boolean isAcceptsRangeAddon() {
        return acceptsRangeAddon;
    }

    @Override
    public List<String> getTooltip(ItemStack stack) {
        List<String> tooltips = super.getTooltip(stack);
        if (acceptsRangeAddon)
            tooltips.add(new TextComponentTranslation("text.industrialforegoing.tooltip.accepts_range_addons").getFormattedText() + TextFormatting.GRAY + " (" + new TextComponentTranslation("text.industrialforegoing.tooltip.max_tier").getFormattedText() + TextFormatting.GRAY + " " + getMaxWidth() + ")");
        else
            tooltips.add(new TextComponentTranslation("text.industrialforegoing.tooltip.range").getFormattedText().replace("{0}", "" + (maxWidth * 2 + 1)).replace("{1}", (height + 1) + ""));
        return tooltips;
    }

    public enum RangeType {
        FRONT {
            @Override
            public AxisAlignedBB getArea(BlockPos pos, int width, int height, EnumFacing f) {
                return WorkUtils.generateBlockSizeBox(pos).offset(new BlockPos(0, 0, 0).offset(f.getOpposite(), width + 1)).grow(width, 0, width).setMaxY(pos.getY() + 1 + height);
            }
        }, UP {
            @Override
            public AxisAlignedBB getArea(BlockPos pos, int width, int height, EnumFacing f) {
                return WorkUtils.generateBlockSizeBox(pos).offset(0, 1, 0).grow(width, height, width);
            }
        };

        public abstract AxisAlignedBB getArea(BlockPos pos, int width, int height, EnumFacing facing);

    }
}

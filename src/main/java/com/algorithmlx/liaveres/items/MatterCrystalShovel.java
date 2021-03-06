package com.algorithmlx.liaveres.items;

import com.algorithmlx.liaveres.itemtears.MatterTear;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

import static com.algorithmlx.liaveres.setup.ModSetup.LIAVERES_ALL;

public class MatterCrystalShovel extends ShovelItem {
    public MatterCrystalShovel()
    {
        super(MatterTear.MatterTear, 3000,99999999999999999999999999999999999999F, new Properties().isImmuneToFire().group(LIAVERES_ALL));
    }
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.matter_crystal_shovel"));
    }
}

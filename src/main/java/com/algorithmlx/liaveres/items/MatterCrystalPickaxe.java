package com.algorithmlx.liaveres.items;

import com.algorithmlx.liaveres.itemtears.MatterTear;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.algorithmlx.liaveres.setup.ModSetup.LIAVERES_ALL;


public class MatterCrystalPickaxe extends PickaxeItem {
    public static boolean skip = false;

    public MatterCrystalPickaxe() {
        super(MatterTear.MatterTear, 30, 99999999999999999999999999999999999999F, new Properties().isImmuneToFire().group(LIAVERES_ALL));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getPos();
        ItemStack itemstack = context.getItem();
        if (player == null) return ActionResultType.FAIL;
        World world = context.getPlayer().getEntityWorld();
        if (!world.isRemote() && !player.isSneaking() && !player.getCooldownTracker().hasCooldown(itemstack.getItem()) && player instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

            if (!skip) {
                List<BlockPos> blocks = new ArrayList<>();

                for (int x = 0; x < 31; x++) {
                    for (int y = 0; y < 31; y++) {
                        for (int z = 0; z < 31; z++) {
                            int posX = pos.getX();
                            int posY = pos.getY();
                            int posZ = pos.getZ();

                            switch (player.getHorizontalFacing()) {
                                case SOUTH:
                                    blocks.add(new BlockPos(posX + 16 - x, posY - 1 + y, posZ + z));
                                    break;
                                case NORTH:
                                    blocks.add(new BlockPos(posX - 16 + x, posY - 1 + y, posZ - z));
                                    break;
                                case EAST:
                                    blocks.add(new BlockPos(posX + x, posY - 1 + y, posZ + 16 - z));
                                    break;
                                case WEST:
                                    blocks.add(new BlockPos(posX - x, posY - 1 + y, posZ - 16 + z));
                                    break;
                            }
                        }
                    }
                }
                skip = true;
                for (BlockPos position : blocks) {
                    BlockState state = world.getBlockState(position);
                    if (!state.isAir()) {
                        serverPlayer.interactionManager.tryHarvestBlock(position);
                        
                    }
                }
                skip = false;
            }
            player.getCooldownTracker().setCooldown(this, 240);
        }
        return super.onItemUse(context);
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.matter_crystal_pickaxe1"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.matter_crystal_pickaxe2"));
    }
}
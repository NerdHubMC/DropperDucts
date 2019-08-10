package nerdhub.dropperducts.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DropperBlock.class)
public class MixinDropperChain {

    @Inject(method = "dispense", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER))
    private void onDispense(World world, BlockPos pos, CallbackInfo ci) {
        Direction ownFacing = world.getBlockState(pos).get(DropperBlock.FACING);
        BlockPos side = pos.offset(ownFacing);
        BlockState state = world.getBlockState(side);
        if(state.getBlock() instanceof DispenserBlock) {
            world.getBlockTickScheduler().schedule(side, state.getBlock(), state.getBlock().getTickRate(world));
        }
    }

}

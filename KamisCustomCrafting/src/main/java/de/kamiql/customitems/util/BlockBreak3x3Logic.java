package de.kamiql.customitems.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BlockBreak3x3Logic {
    private Player player;
    private Block baseBlock;
    private static final int SIZE = 3;

    public BlockBreak3x3Logic(Player player, Block baseBlock) {
        this.player = player;
        this.baseBlock = baseBlock;
    }

    public void run() {
        ItemStack item = player.getInventory().getItemInMainHand();
        BlockFace blockFace = getBlockFace(player);

        if (blockFace != null && item.getItemMeta() instanceof Damageable) {
            Block[] blocksToBreak = getSurroundingBlocks(blockFace);

            Material[] banned_blocks = {
                    Material.BEDROCK, Material.END_PORTAL, Material.END_PORTAL_FRAME, Material.COMMAND_BLOCK, Material.STRUCTURE_BLOCK, Material.BARRIER
            };

            for (Block block : blocksToBreak) {
                Material blockType = block.getType();

                boolean isBanned = false;
                for (Material bannedMaterial : banned_blocks) {
                    if (blockType == bannedMaterial) {
                        isBanned = true;
                        break;
                    }
                }

                if (!isBanned) {
                    block.breakNaturally(item);
                }
            }

            Damageable meta = (Damageable) item.getItemMeta();
            meta.setDamage(meta.getDamage() + (int) (blocksToBreak.length * 0.25));
            item.setItemMeta(meta);

            if (meta.getDamage() >= item.getType().getMaxDurability()) {
                player.getInventory().remove(item);
            }
        }
    }

    private Block[] getSurroundingBlocks(BlockFace blockFace) {
        List<Block> blocks = new ArrayList<>();
        int halfSize = SIZE / 2;

        for (int y = -halfSize; y <= halfSize; y++) {
            for (int x = -halfSize; x <= halfSize; x++) {
                Block relativeBlock;
                switch (blockFace) {
                    case EAST:
                    case WEST:
                        relativeBlock = baseBlock.getRelative(0, y, x);
                        break;
                    case NORTH:
                    case SOUTH:
                        relativeBlock = baseBlock.getRelative(x, y, 0);
                        break;
                    case UP:
                    case DOWN:
                        Vector direction = player.getEyeLocation().getDirection();
                        if (Math.abs(direction.getX()) > Math.abs(direction.getZ())) {
                            relativeBlock = baseBlock.getRelative(y, 0, x);
                        } else {
                            relativeBlock = baseBlock.getRelative(x, 0, y);
                        }
                        break;
                    default:
                        relativeBlock = baseBlock;
                }
                blocks.add(relativeBlock);
            }
        }

        return blocks.toArray(new Block[0]);
    }

    private BlockFace getBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 5);
        if (lastTwoTargetBlocks.size() != 2) return null;

        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);

        return targetBlock.getFace(adjacentBlock);
    }
}
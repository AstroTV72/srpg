package me.astro.srpg.components;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

import static me.astro.srpg.Utils.*;

public enum CustomMob {

    DESERT_RISEN("&6Désert ressuscité", 15, 60, EntityType.HUSK, null, null, new LootItem(createItem(Material.ROTTEN_FLESH, 1, false, false, false, "&fChair préservée", "&7Une chair préservée d’un cadavre en décomposition", "&7Je ne sais pas pour quoi vous voudriez cela, cependant", "&7", "&9Foodstuff"), 1, 3, 100)),

    SKELETAL_MAGE("&dMage Squelette", 20, 15, EntityType.SKELETON, createItem(Material.BONE, 1, true, false, false, null), makeArmorSet(new ItemStack(Material.IRON_HELMET), null, null, null), new LootItem(createItem(Material.BONE, 1, true, false, false, "&dBaguette d'os", "&7Une baguette faite d’os squelettiques"), 30), new LootItem(new ItemStack(Material.BONE), 1, 3, 100)),

    ZOMBIE_SQUIRE("&bÉcuyer zombie", 20, 12, EntityType.ZOMBIE, new ItemStack(Material.IRON_SWORD), makeArmorSet(new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.IRON_BOOTS)), new LootItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), 35), new LootItem(new ItemStack(Material.CHAINMAIL_LEGGINGS), 35), new LootItem(new ItemStack(Material.CHAINMAIL_HELMET), 35), new LootItem(new ItemStack(Material.IRON_BOOTS), 25), new LootItem(new ItemStack(Material.IRON_SWORD), 40)),

    CHARRED_ARCHER("&8Archer carbonisé", 50, 3, EntityType.WITHER_SKELETON, enchantItem(new ItemStack(Material.BOW), Enchantment.ARROW_KNOCKBACK, 2), null, new LootItem(enchantItem(enchantItem(createItem(Material.BOW, 1, false, false, false, "&cArc brûlé", "&7Cet arc est brûlé à un croustillant mais reste intact", "&8en raison d’enchantements spéciaux"), Enchantment.ARROW_FIRE, 1), Enchantment.ARROW_KNOCKBACK, 2), 100), new LootItem(new ItemStack(Material.BONE), 1, 5, 100)),
    ;

    private String name;
    private double maxHealth, spawnChance;
    private EntityType type;
    private ItemStack mainItem;
    private ItemStack[] armor;
    private List<LootItem> lootTable;

    CustomMob(String name, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor, LootItem... lootItems) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.type = type;
        this.mainItem = mainItem;
        this.armor = armor;
        lootTable = Arrays.asList(lootItems);
    }

    public LivingEntity spawn(Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(color(name + " &r&c" + (int) maxHealth + "/" + (int) maxHealth + "❤"));
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);
        EntityEquipment inv = entity.getEquipment();
        if (armor != null) inv.setArmorContents(armor);
        inv.setHelmetDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);
        return entity;
    }

    public void tryDropLoot(Location location) {
        for (LootItem item : lootTable) {
            item.tryDropItem(location);
        }
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getSpawnChance() {
        return spawnChance;
    }

}

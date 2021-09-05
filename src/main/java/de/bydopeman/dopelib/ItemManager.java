package de.bydopeman.dopelib;


import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.UUID;

public class ItemManager {


    private ItemStack is;

    public ItemManager(Material material){
        is = new ItemStack(material);
    }

    public ItemManager(Material material, int amount){
        is = new ItemStack(material, amount);
    }

    public ItemMeta getItemMeta() {
        return is.getItemMeta();
    }

    public ItemManager setColor(Color color){
        LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
        meta.setColor(color);
        setItemMeta(meta);
        return this;
    }

    public ItemManager setGlow(boolean glow){
        if(glow){
            addEnchant(Enchantment.DURABILITY, 1);
            addItemFlag(ItemFlag.HIDE_ENCHANTS);
        } else {
            ItemMeta meta = getItemMeta();
            for(Enchantment enchantment : meta.getEnchants().keySet()){
                meta.removeEnchant(enchantment);
            }
        }
        return this;
    }

    public ItemManager setUnbreakable(boolean unbreakable){
        ItemMeta meta = is.getItemMeta();
        meta.setUnbreakable(unbreakable);
        is.setItemMeta(meta);
        return this;
    }

    public ItemManager setBannerColor(DyeColor color){
        BannerMeta meta = (BannerMeta) is.getItemMeta();
        meta.setBaseColor(color);
        setItemMeta(meta);
        return this;
    }

    public ItemManager setAmount(int amount){
        is.setAmount(amount);
        return this;
    }

    public ItemManager setItemMeta(ItemMeta meta){
        is.setItemMeta(meta);
        return this;
    }

    public ItemManager setHeadOwner(String Owner){
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        meta.setOwner(Owner);
        setItemMeta(meta);
        return this;
    }

    public ItemManager setSkullURL(String url){
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e){
            e.printStackTrace();
        }
        setItemMeta(meta);
        return this;
    }

    public ItemManager setDisplayName(String displayName){
        ItemMeta meta = getItemMeta();
        meta.setDisplayName(displayName);
        setItemMeta(meta);
        return this;
    }

    public ItemManager setItemStack(ItemStack itemStack){
        this.is = itemStack;
        return this;
    }

    public ItemManager setLore(ArrayList<String> lore){
        ItemMeta meta = getItemMeta();
        meta.setLore(lore);
        setItemMeta(meta);
        return this;
    }

    public ItemManager setLore(String lore){
        ArrayList<String> lorelist = new ArrayList<>();
        lorelist.add(lore);
        ItemMeta meta = getItemMeta();
        meta.setLore(lorelist);
        setItemMeta(meta);
        return this;
    }

    public ItemManager addEnchant(Enchantment enchantment, int level){
        ItemMeta meta = getItemMeta();
        meta.addEnchant(enchantment, level, true);
        setItemMeta(meta);
        return this;
    }

    public ItemManager addItemFlag(ItemFlag flag){
        ItemMeta meta = getItemMeta();
        meta.addItemFlags(flag);
        setItemMeta(meta);
        return this;
    }

    public ItemStack build(){
        return is;
    }

}

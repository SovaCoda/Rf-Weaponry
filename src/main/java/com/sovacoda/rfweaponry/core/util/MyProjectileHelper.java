package com.sovacoda.rfweaponry.core.util;

import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MyProjectileHelper {
	   public static RayTraceResult getHitResult(Entity shooter, Predicate<Entity> predicate, Vector3d target) {
		      World world = shooter.level;
		      RayTraceResult raytraceresult = world.clip(new RayTraceContext(shooter.getPosition(0), target, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, shooter));
		      if (raytraceresult.getType() != RayTraceResult.Type.MISS) {
		         target = raytraceresult.getLocation();
		      }

		      RayTraceResult raytraceresult1 = ProjectileHelper.getEntityHitResult(world, shooter, shooter.getPosition(0), target, shooter.getBoundingBox(), predicate);
		      if (raytraceresult1 != null) {
		         raytraceresult = raytraceresult1;
		      }

		      return raytraceresult;
		   }
}

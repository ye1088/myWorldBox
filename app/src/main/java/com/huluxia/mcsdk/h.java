package com.huluxia.mcsdk;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.support.annotation.y;
import java.util.List;

/* compiled from: WrappedPackageManager */
public class h extends PackageManager {
    protected PackageManager aop;

    public h(PackageManager wrapped) {
        this.aop = wrapped;
    }

    public PackageInfo getPackageInfo(String packageName, int flags) throws NameNotFoundException {
        return this.aop.getPackageInfo(packageName, flags);
    }

    public String[] currentToCanonicalPackageNames(String[] names) {
        return this.aop.currentToCanonicalPackageNames(names);
    }

    public String[] canonicalToCurrentPackageNames(String[] names) {
        return this.aop.canonicalToCurrentPackageNames(names);
    }

    public Intent getLaunchIntentForPackage(String packageName) {
        return this.aop.getLaunchIntentForPackage(packageName);
    }

    public Intent getLeanbackLaunchIntentForPackage(String s) {
        return null;
    }

    public int[] getPackageGids(String packageName) throws NameNotFoundException {
        return this.aop.getPackageGids(packageName);
    }

    public PermissionInfo getPermissionInfo(String name, int flags) throws NameNotFoundException {
        return this.aop.getPermissionInfo(name, flags);
    }

    public List<PermissionInfo> queryPermissionsByGroup(String group, int flags) throws NameNotFoundException {
        return this.aop.queryPermissionsByGroup(group, flags);
    }

    public PermissionGroupInfo getPermissionGroupInfo(String name, int flags) throws NameNotFoundException {
        return this.aop.getPermissionGroupInfo(name, flags);
    }

    public List<PermissionGroupInfo> getAllPermissionGroups(int flags) {
        return this.aop.getAllPermissionGroups(flags);
    }

    public ApplicationInfo getApplicationInfo(String packageName, int flags) throws NameNotFoundException {
        return this.aop.getApplicationInfo(packageName, flags);
    }

    public ActivityInfo getActivityInfo(ComponentName className, int flags) throws NameNotFoundException {
        return this.aop.getActivityInfo(className, flags);
    }

    public ActivityInfo getReceiverInfo(ComponentName className, int flags) throws NameNotFoundException {
        return this.aop.getReceiverInfo(className, flags);
    }

    public ServiceInfo getServiceInfo(ComponentName className, int flags) throws NameNotFoundException {
        return this.aop.getServiceInfo(className, flags);
    }

    public ProviderInfo getProviderInfo(ComponentName className, int flags) throws NameNotFoundException {
        return this.aop.getProviderInfo(className, flags);
    }

    public List<PackageInfo> getInstalledPackages(int flags) {
        return this.aop.getInstalledPackages(flags);
    }

    public int checkPermission(String permName, String pkgName) {
        return this.aop.checkPermission(permName, pkgName);
    }

    public boolean F(String s, String s1) {
        return false;
    }

    public boolean addPermission(PermissionInfo info) {
        return this.aop.addPermission(info);
    }

    public boolean addPermissionAsync(PermissionInfo info) {
        return this.aop.addPermissionAsync(info);
    }

    public void removePermission(String name) {
        this.aop.removePermission(name);
    }

    public int checkSignatures(String pkg1, String pkg2) {
        return this.aop.checkSignatures(pkg1, pkg2);
    }

    public int checkSignatures(int uid1, int uid2) {
        return this.aop.checkSignatures(uid1, uid2);
    }

    public String[] getPackagesForUid(int uid) {
        return this.aop.getPackagesForUid(uid);
    }

    public String getNameForUid(int uid) {
        return this.aop.getNameForUid(uid);
    }

    public List<ApplicationInfo> getInstalledApplications(int flags) {
        return this.aop.getInstalledApplications(flags);
    }

    public ResolveInfo resolveActivity(Intent intent, int flags) {
        return this.aop.resolveActivity(intent, flags);
    }

    public List<ResolveInfo> queryIntentActivities(Intent intent, int flags) {
        return this.aop.queryIntentActivities(intent, flags);
    }

    public List<ResolveInfo> queryIntentActivityOptions(ComponentName caller, Intent[] specifics, Intent intent, int flags) {
        return this.aop.queryIntentActivityOptions(caller, specifics, intent, flags);
    }

    public List<ResolveInfo> queryBroadcastReceivers(Intent intent, int flags) {
        return this.aop.queryBroadcastReceivers(intent, flags);
    }

    public ResolveInfo resolveService(Intent intent, int flags) {
        return this.aop.resolveService(intent, flags);
    }

    public List<ResolveInfo> queryIntentServices(Intent intent, int flags) {
        return this.aop.queryIntentServices(intent, flags);
    }

    public ProviderInfo resolveContentProvider(String name, int flags) {
        return this.aop.resolveContentProvider(name, flags);
    }

    public List<ProviderInfo> queryContentProviders(String processName, int uid, int flags) {
        return this.aop.queryContentProviders(processName, uid, flags);
    }

    public InstrumentationInfo getInstrumentationInfo(ComponentName className, int flags) throws NameNotFoundException {
        return this.aop.getInstrumentationInfo(className, flags);
    }

    public List<InstrumentationInfo> queryInstrumentation(String targetPackage, int flags) {
        return this.aop.queryInstrumentation(targetPackage, flags);
    }

    public Drawable getDrawable(String packageName, int resid, ApplicationInfo appInfo) {
        return this.aop.getDrawable(packageName, resid, appInfo);
    }

    public Drawable getActivityIcon(ComponentName activityName) throws NameNotFoundException {
        return this.aop.getActivityIcon(activityName);
    }

    public Drawable getActivityIcon(Intent intent) throws NameNotFoundException {
        return this.aop.getActivityIcon(intent);
    }

    public Drawable getActivityBanner(ComponentName componentName) throws NameNotFoundException {
        return null;
    }

    public Drawable getActivityBanner(Intent intent) throws NameNotFoundException {
        return null;
    }

    public Drawable getDefaultActivityIcon() {
        return this.aop.getDefaultActivityIcon();
    }

    public Drawable getApplicationIcon(ApplicationInfo info) {
        return this.aop.getApplicationIcon(info);
    }

    public Drawable getApplicationIcon(String packageName) throws NameNotFoundException {
        return this.aop.getApplicationIcon(packageName);
    }

    public Drawable getApplicationBanner(ApplicationInfo applicationInfo) {
        return null;
    }

    public Drawable getApplicationBanner(String s) throws NameNotFoundException {
        return null;
    }

    public Drawable getActivityLogo(ComponentName activityName) throws NameNotFoundException {
        return this.aop.getActivityLogo(activityName);
    }

    public Drawable getActivityLogo(Intent intent) throws NameNotFoundException {
        return this.aop.getActivityLogo(intent);
    }

    public Drawable getApplicationLogo(ApplicationInfo info) {
        return this.aop.getApplicationLogo(info);
    }

    public Drawable getApplicationLogo(String packageName) throws NameNotFoundException {
        return this.aop.getApplicationLogo(packageName);
    }

    public Drawable getUserBadgedIcon(Drawable drawable, UserHandle userHandle) {
        return null;
    }

    public Drawable getUserBadgedDrawableForDensity(Drawable drawable, UserHandle userHandle, Rect rect, int i) {
        return null;
    }

    public CharSequence getUserBadgedLabel(CharSequence charSequence, UserHandle userHandle) {
        return null;
    }

    public CharSequence getText(String packageName, int resid, ApplicationInfo appInfo) {
        return this.aop.getText(packageName, resid, appInfo);
    }

    public XmlResourceParser getXml(String packageName, int resid, ApplicationInfo appInfo) {
        return this.aop.getXml(packageName, resid, appInfo);
    }

    public CharSequence getApplicationLabel(ApplicationInfo info) {
        return this.aop.getApplicationLabel(info);
    }

    public Resources getResourcesForActivity(ComponentName activityName) throws NameNotFoundException {
        return this.aop.getResourcesForActivity(activityName);
    }

    public Resources getResourcesForApplication(ApplicationInfo app) throws NameNotFoundException {
        return this.aop.getResourcesForApplication(app);
    }

    public Resources getResourcesForApplication(String appPackageName) throws NameNotFoundException {
        return this.aop.getResourcesForApplication(appPackageName);
    }

    public PackageInfo getPackageArchiveInfo(String archiveFilePath, int flags) {
        return this.aop.getPackageArchiveInfo(archiveFilePath, flags);
    }

    public String getInstallerPackageName(String packageName) {
        return this.aop.getInstallerPackageName(packageName);
    }

    public void addPackageToPreferred(String packageName) {
        this.aop.addPackageToPreferred(packageName);
    }

    public void removePackageFromPreferred(String packageName) {
        this.aop.removePackageFromPreferred(packageName);
    }

    public List<PackageInfo> getPreferredPackages(int flags) {
        return this.aop.getPreferredPackages(flags);
    }

    public void setComponentEnabledSetting(ComponentName componentName, int newState, int flags) {
        this.aop.setComponentEnabledSetting(componentName, newState, flags);
    }

    public int getComponentEnabledSetting(ComponentName componentName) {
        return this.aop.getComponentEnabledSetting(componentName);
    }

    public void setApplicationEnabledSetting(String packageName, int newState, int flags) {
        this.aop.setApplicationEnabledSetting(packageName, newState, flags);
    }

    public int getApplicationEnabledSetting(String packageName) {
        return this.aop.getApplicationEnabledSetting(packageName);
    }

    public void addPreferredActivity(IntentFilter filter, int match, ComponentName[] set, ComponentName activity) {
        this.aop.addPreferredActivity(filter, match, set, activity);
    }

    public void clearPackagePreferredActivities(String packageName) {
        this.aop.clearPackagePreferredActivities(packageName);
    }

    public int getPreferredActivities(List<IntentFilter> outFilters, List<ComponentName> outActivities, String packageName) {
        return this.aop.getPreferredActivities(outFilters, outActivities, packageName);
    }

    public String[] getSystemSharedLibraryNames() {
        return this.aop.getSystemSharedLibraryNames();
    }

    public FeatureInfo[] getSystemAvailableFeatures() {
        return this.aop.getSystemAvailableFeatures();
    }

    public boolean hasSystemFeature(String name) {
        return this.aop.hasSystemFeature(name);
    }

    public boolean isSafeMode() {
        return this.aop.isSafeMode();
    }

    @y
    public PackageInstaller getPackageInstaller() {
        return null;
    }

    public List<PackageInfo> getPackagesHoldingPermissions(String[] permissions, int flags) {
        return this.aop.getPackagesHoldingPermissions(permissions, flags);
    }

    public List<ResolveInfo> queryIntentContentProviders(Intent intent, int flags) {
        return this.aop.queryIntentContentProviders(intent, flags);
    }

    public void verifyPendingInstall(int id, int verificationCode) {
        this.aop.verifyPendingInstall(id, verificationCode);
    }

    public void extendVerificationTimeout(int id, int verificationCodeAtTimeout, long millisecondsToDelay) {
        this.aop.extendVerificationTimeout(id, verificationCodeAtTimeout, millisecondsToDelay);
    }

    public void setInstallerPackageName(String targetPackage, String installerPackageName) {
        this.aop.setInstallerPackageName(targetPackage, installerPackageName);
    }
}

package io.github.mattshoe.shoebox.stratify.ksp

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.KSBuiltIns
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.*
import io.github.mattshoe.shoebox.stratify.util.ensureOnMainThread

internal class StratifyResolverImpl(
    private val resolver: Resolver
): Resolver {
    override val builtIns: KSBuiltIns
        get() = run {
            ensureOnMainThread()
            resolver.builtIns
        }

    override fun createKSTypeReferenceFromKSType(type: KSType): KSTypeReference {
        ensureOnMainThread()
        return resolver.createKSTypeReferenceFromKSType(type)
    }

    @KspExperimental
    override fun effectiveJavaModifiers(declaration: KSDeclaration): Set<Modifier> {
        ensureOnMainThread()
        return resolver.effectiveJavaModifiers(declaration)
    }

    override fun getAllFiles(): Sequence<KSFile> {
        ensureOnMainThread()
        return resolver.getAllFiles()
    }

    override fun getClassDeclarationByName(name: KSName): KSClassDeclaration? {
        ensureOnMainThread()
        return resolver.getClassDeclarationByName(name)
    }

    @KspExperimental
    override fun getDeclarationsFromPackage(packageName: String): Sequence<KSDeclaration> {
        ensureOnMainThread()
        return resolver.getDeclarationsFromPackage(packageName)
    }

    @KspExperimental
    override fun getDeclarationsInSourceOrder(container: KSDeclarationContainer): Sequence<KSDeclaration> {
        ensureOnMainThread()
        return resolver.getDeclarationsInSourceOrder(container)
    }

    override fun getFunctionDeclarationsByName(
        name: KSName,
        includeTopLevel: Boolean
    ): Sequence<KSFunctionDeclaration> {
        ensureOnMainThread()
        return resolver.getFunctionDeclarationsByName(name, includeTopLevel)
    }

    @KspExperimental
    override fun getJavaWildcard(reference: KSTypeReference): KSTypeReference {
        ensureOnMainThread()
        return resolver.getJavaWildcard(reference)
    }

    @KspExperimental
    override fun getJvmCheckedException(function: KSFunctionDeclaration): Sequence<KSType> {
        ensureOnMainThread()
        return resolver.getJvmCheckedException(function)
    }

    @KspExperimental
    override fun getJvmCheckedException(accessor: KSPropertyAccessor): Sequence<KSType> {
        ensureOnMainThread()
        return resolver.getJvmCheckedException(accessor)
    }

    @KspExperimental
    override fun getJvmName(declaration: KSFunctionDeclaration): String? {
        ensureOnMainThread()
        return resolver.getJvmName(declaration)
    }

    @KspExperimental
    override fun getJvmName(accessor: KSPropertyAccessor): String? {
        ensureOnMainThread()
        return resolver.getJvmName(accessor)
    }

    override fun getKSNameFromString(name: String): KSName {
        ensureOnMainThread()
        return resolver.getKSNameFromString(name)
    }

    @KspExperimental
    override fun getModuleName(): KSName {
        ensureOnMainThread()
        return resolver.getModuleName()
    }

    override fun getNewFiles(): Sequence<KSFile> {
        ensureOnMainThread()
        return resolver.getNewFiles()
    }

    @KspExperimental
    override fun getOwnerJvmClassName(declaration: KSFunctionDeclaration): String? {
        ensureOnMainThread()
        return resolver.getOwnerJvmClassName(declaration)
    }

    @KspExperimental
    override fun getOwnerJvmClassName(declaration: KSPropertyDeclaration): String? {
        ensureOnMainThread()
        return resolver.getOwnerJvmClassName(declaration)
    }

    @KspExperimental
    override fun getPackageAnnotations(packageName: String): Sequence<KSAnnotation> {
        ensureOnMainThread()
        return resolver.getPackageAnnotations(packageName)
    }

    @KspExperimental
    override fun getPackagesWithAnnotation(annotationName: String): Sequence<String> {
        ensureOnMainThread()
        return resolver.getPackagesWithAnnotation(annotationName)
    }

    override fun getPropertyDeclarationByName(name: KSName, includeTopLevel: Boolean): KSPropertyDeclaration? {
        ensureOnMainThread()
        return resolver.getPropertyDeclarationByName(name, includeTopLevel)
    }

    override fun getSymbolsWithAnnotation(annotationName: String, inDepth: Boolean): Sequence<KSAnnotated> {
        ensureOnMainThread()
        return resolver.getSymbolsWithAnnotation(annotationName, inDepth)
    }

    override fun getTypeArgument(typeRef: KSTypeReference, variance: Variance): KSTypeArgument {
        ensureOnMainThread()
        return resolver.getTypeArgument(typeRef, variance)
    }

    @KspExperimental
    override fun isJavaRawType(type: KSType): Boolean {
        ensureOnMainThread()
        return resolver.isJavaRawType(type)
    }

    @KspExperimental
    override fun mapJavaNameToKotlin(javaName: KSName): KSName? {
        ensureOnMainThread()
        return resolver.mapJavaNameToKotlin(javaName)
    }

    @KspExperimental
    override fun mapKotlinNameToJava(kotlinName: KSName): KSName? {
        ensureOnMainThread()
        return resolver.mapKotlinNameToJava(kotlinName)
    }

    @KspExperimental
    override fun mapToJvmSignature(declaration: KSDeclaration): String? {
        ensureOnMainThread()
        return resolver.mapToJvmSignature(declaration)
    }

    override fun overrides(overrider: KSDeclaration, overridee: KSDeclaration): Boolean {
        ensureOnMainThread()
        return resolver.overrides(overrider, overridee)
    }

    override fun overrides(
        overrider: KSDeclaration,
        overridee: KSDeclaration,
        containingClass: KSClassDeclaration
    ): Boolean {
        ensureOnMainThread()
        return resolver.overrides(overrider, overridee, containingClass)
    }

}
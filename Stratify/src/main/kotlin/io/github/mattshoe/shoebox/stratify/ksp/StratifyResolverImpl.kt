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
            ensureMain()
            resolver.builtIns
        }

    override fun createKSTypeReferenceFromKSType(type: KSType): KSTypeReference {
        ensureMain()
        return resolver.createKSTypeReferenceFromKSType(type)
    }

    @KspExperimental
    override fun effectiveJavaModifiers(declaration: KSDeclaration): Set<Modifier> {
        ensureMain()
        return resolver.effectiveJavaModifiers(declaration)
    }

    override fun getAllFiles(): Sequence<KSFile> {
        ensureMain()
        return resolver.getAllFiles()
    }

    override fun getClassDeclarationByName(name: KSName): KSClassDeclaration? {
        ensureMain()
        return resolver.getClassDeclarationByName(name)
    }

    @KspExperimental
    override fun getDeclarationsFromPackage(packageName: String): Sequence<KSDeclaration> {
        ensureMain()
        return resolver.getDeclarationsFromPackage(packageName)
    }

    @KspExperimental
    override fun getDeclarationsInSourceOrder(container: KSDeclarationContainer): Sequence<KSDeclaration> {
        ensureMain()
        return resolver.getDeclarationsInSourceOrder(container)
    }

    override fun getFunctionDeclarationsByName(
        name: KSName,
        includeTopLevel: Boolean
    ): Sequence<KSFunctionDeclaration> {
        ensureMain()
        return resolver.getFunctionDeclarationsByName(name, includeTopLevel)
    }

    @KspExperimental
    override fun getJavaWildcard(reference: KSTypeReference): KSTypeReference {
        ensureMain()
        return resolver.getJavaWildcard(reference)
    }

    @KspExperimental
    override fun getJvmCheckedException(function: KSFunctionDeclaration): Sequence<KSType> {
        ensureMain()
        return resolver.getJvmCheckedException(function)
    }

    @KspExperimental
    override fun getJvmCheckedException(accessor: KSPropertyAccessor): Sequence<KSType> {
        ensureMain()
        return resolver.getJvmCheckedException(accessor)
    }

    @KspExperimental
    override fun getJvmName(declaration: KSFunctionDeclaration): String? {
        ensureMain()
        return resolver.getJvmName(declaration)
    }

    @KspExperimental
    override fun getJvmName(accessor: KSPropertyAccessor): String? {
        ensureMain()
        return resolver.getJvmName(accessor)
    }

    override fun getKSNameFromString(name: String): KSName {
        ensureMain()
        return resolver.getKSNameFromString(name)
    }

    @KspExperimental
    override fun getModuleName(): KSName {
        ensureMain()
        return resolver.getModuleName()
    }

    override fun getNewFiles(): Sequence<KSFile> {
        ensureMain()
        return resolver.getNewFiles()
    }

    @KspExperimental
    override fun getOwnerJvmClassName(declaration: KSFunctionDeclaration): String? {
        ensureMain()
        return resolver.getOwnerJvmClassName(declaration)
    }

    @KspExperimental
    override fun getOwnerJvmClassName(declaration: KSPropertyDeclaration): String? {
        ensureMain()
        return resolver.getOwnerJvmClassName(declaration)
    }

    @KspExperimental
    override fun getPackageAnnotations(packageName: String): Sequence<KSAnnotation> {
        ensureMain()
        return resolver.getPackageAnnotations(packageName)
    }

    @KspExperimental
    override fun getPackagesWithAnnotation(annotationName: String): Sequence<String> {
        ensureMain()
        return resolver.getPackagesWithAnnotation(annotationName)
    }

    override fun getPropertyDeclarationByName(name: KSName, includeTopLevel: Boolean): KSPropertyDeclaration? {
        ensureMain()
        return resolver.getPropertyDeclarationByName(name, includeTopLevel)
    }

    override fun getSymbolsWithAnnotation(annotationName: String, inDepth: Boolean): Sequence<KSAnnotated> {
        ensureMain()
        return resolver.getSymbolsWithAnnotation(annotationName, inDepth)
    }

    override fun getTypeArgument(typeRef: KSTypeReference, variance: Variance): KSTypeArgument {
        ensureMain()
        return resolver.getTypeArgument(typeRef, variance)
    }

    @KspExperimental
    override fun isJavaRawType(type: KSType): Boolean {
        ensureMain()
        return resolver.isJavaRawType(type)
    }

    @KspExperimental
    override fun mapJavaNameToKotlin(javaName: KSName): KSName? {
        ensureMain()
        return resolver.mapJavaNameToKotlin(javaName)
    }

    @KspExperimental
    override fun mapKotlinNameToJava(kotlinName: KSName): KSName? {
        ensureMain()
        return resolver.mapKotlinNameToJava(kotlinName)
    }

    @KspExperimental
    override fun mapToJvmSignature(declaration: KSDeclaration): String? {
        ensureMain()
        return resolver.mapToJvmSignature(declaration)
    }

    override fun overrides(overrider: KSDeclaration, overridee: KSDeclaration): Boolean {
        ensureMain()
        return resolver.overrides(overrider, overridee)
    }

    override fun overrides(
        overrider: KSDeclaration,
        overridee: KSDeclaration,
        containingClass: KSClassDeclaration
    ): Boolean {
        ensureMain()
        return resolver.overrides(overrider, overridee, containingClass)
    }
    
    private fun ensureMain() {
        ensureOnMainThread(Resolver::class.simpleName.toString())
    }

}
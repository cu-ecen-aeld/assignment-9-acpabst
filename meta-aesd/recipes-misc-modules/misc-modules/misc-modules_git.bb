# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://github.com/cu-ecen-aeld/assignment-7-acpabst.git;protocol=https;branch=master \
           file://module_init \
           "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "84efa0a23681ff4c04e49a0c14ff4aea84b04cab"

S = "${WORKDIR}/git/misc-modules"

inherit module
MODULES_INSTALL_TARGET = "install"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/misc-modules"

inherit update-rc.d
FILES:${PN} += "${bindir}/module_load"
FILES:${PN} += "${bindir}/module_unload"
FILES:${PN} += "${sysconfdir}/init.d/module_init"
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "module_init"

do_configure () {
        :
}

do_compile () {
        oe_runmake
}

do_install () {
        install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
	install -m 0755 ${S}/hello.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    	install -m 0755 ${S}/faulty.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
       
	install -d ${D}${bindir}
	install -m 0755 ${S}/module_load ${D}${bindir}/ 
	install -m 0755 ${S}/module_unload ${D}${bindir}/
	
	install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/module_init ${D}${sysconfdir}/init.d
}

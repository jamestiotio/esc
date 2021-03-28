#!/bin/sh
# This script can be used to install KLEE with LLVM 9, Clang, Z3 and uClibc on Ubuntu 18.04
# All the corresponding cloned git directories will be placed at the home folder of the current user
# This script is specifically for the Bash shell, and thus if you use other shells, modify `.bashrc` accordingly to the appropriate filename
# Created by James Raphael Tiovalen (2021)

# Move to home directory of current user
cd $HOME

# Install dependencies
sudo apt install -y build-essential curl libcap-dev git cmake libncurses5-dev python-minimal python-pip unzip libtcmalloc-minimal4 libgoogle-perftools-dev libsqlite3-dev doxygen python3 python3-pip gcc-multilib g++-multilib
pip3 install lit tabulate wllvm

# Install LLVM 9
sudo apt install -y clang-9 llvm-9 llvm-9-dev llvm-9-tools
export LLVM_CONFIG=/usr/bin/llvm-config-9
echo "export LLVM_CONFIG=/usr/bin/llvm-config-9" >> $HOME/.bashrc

# Install Z3 constraint solver/theorem prover
git clone https://github.com/Z3Prover/z3.git
cd z3/
python3 scripts/mk_make.py
cd build/
make
sudo make install
cd ..

# Build uClibc and the POSIX environment model
git clone https://github.com/klee/klee-uclibc.git
cd klee-uclibc/
./configure --make-llvm-lib --with-llvm-config /usr/bin/llvm-config-9
make -j20
cd ..

# Build libcxx/libc++
sudo mkdir /usr/local/libcxx
sudo chown $USER /usr/local/libcxx
git clone https://github.com/klee/klee.git
cd klee/
LLVM_VERSION=9 SANITIZER_BUILD= BASE=/usr/local/libcxx REQUIRES_RTTI=1 DISABLE_ASSERTIONS=1 ENABLE_DEBUG=0 ENABLE_OPTIMIZED=1 ./scripts/build/build.sh libcxx
cd ..

# Install KLEE
cd klee/
mkdir build
cd build/
cmake \
    -DENABLE_SOLVER_Z3=ON \
    -DENABLE_POSIX_RUNTIME=ON \
    -DENABLE_KLEE_UCLIBC=ON \
    -DKLEE_UCLIBC_PATH=$HOME/klee-uclibc \
    -DLLVM_CONFIG_BINARY=/usr/bin/llvm-config-9 \
    -DLLVMCC=/usr/bin/clang-9 \
    -DLLVMCXX=/usr/bin/clang++-9 \
    -DENABLE_KLEE_LIBCXX=ON -DKLEE_LIBCXX_DIR=/usr/local/libcxx/libc++-install-90/ -DKLEE_LIBCXX_INCLUDE_DIR=/usr/local/libcxx/libc++-install-90/include/c++/v1/ \
    -DENABLE_KLEE_EH_CXX=ON -DKLEE_LIBCXXABI_SRC_DIR=/usr/local/libcxx/llvm-90/libcxxabi/ \
    ..
make -j20
sudo make install
export KLEE_DIR=$HOME/klee
echo "export KLEE_DIR=$HOME/klee" >> $HOME/.bashrc
cd ..

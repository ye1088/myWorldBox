package io.netty.handler.codec.compression;

final class Bzip2HuffmanAllocator {
    private static int first(int[] array, int i, int nodesToMove) {
        int length = array.length;
        int limit = i;
        int k = array.length - 2;
        while (i >= nodesToMove && array[i] % length > limit) {
            k = i;
            i -= (limit - i) + 1;
        }
        i = Math.max(nodesToMove - 1, i);
        while (k > i + 1) {
            int temp = (i + k) >>> 1;
            if (array[temp] % length > limit) {
                k = temp;
            } else {
                i = temp;
            }
        }
        return k;
    }

    private static void setExtendedParentPointers(int[] array) {
        int length = array.length;
        array[0] = array[0] + array[1];
        int tailNode = 1;
        int topNode = 2;
        int headNode = 0;
        while (tailNode < length - 1) {
            int temp;
            int headNode2;
            int topNode2;
            if (topNode >= length || array[headNode] < array[topNode]) {
                temp = array[headNode];
                headNode2 = headNode + 1;
                array[headNode] = tailNode;
                headNode = headNode2;
            } else {
                topNode2 = topNode + 1;
                temp = array[topNode];
                topNode = topNode2;
            }
            if (topNode >= length || (headNode < tailNode && array[headNode] < array[topNode])) {
                temp += array[headNode];
                headNode2 = headNode + 1;
                array[headNode] = tailNode + length;
                topNode2 = topNode;
            } else {
                topNode2 = topNode + 1;
                temp += array[topNode];
                headNode2 = headNode;
            }
            array[tailNode] = temp;
            tailNode++;
            topNode = topNode2;
            headNode = headNode2;
        }
    }

    private static int findNodesToRelocate(int[] array, int maximumLength) {
        int currentNode = array.length - 2;
        for (int currentDepth = 1; currentDepth < maximumLength - 1 && currentNode > 1; currentDepth++) {
            currentNode = first(array, currentNode - 1, 0);
        }
        return currentNode;
    }

    private static void allocateNodeLengths(int[] array) {
        int firstNode = array.length - 2;
        int nextNode = array.length - 1;
        int currentDepth = 1;
        int availableNodes = 2;
        while (availableNodes > 0) {
            int lastNode = firstNode;
            firstNode = first(array, lastNode - 1, 0);
            int i = availableNodes - (lastNode - firstNode);
            int nextNode2 = nextNode;
            while (i > 0) {
                nextNode = nextNode2 - 1;
                array[nextNode2] = currentDepth;
                i--;
                nextNode2 = nextNode;
            }
            availableNodes = (lastNode - firstNode) << 1;
            currentDepth++;
            nextNode = nextNode2;
        }
    }

    private static void allocateNodeLengthsWithRelocation(int[] array, int nodesToMove, int insertDepth) {
        int currentDepth;
        int nodesLeftToMove;
        int firstNode = array.length - 2;
        int nextNode = array.length - 1;
        if (insertDepth == 1) {
            currentDepth = 2;
        } else {
            currentDepth = 1;
        }
        if (insertDepth == 1) {
            nodesLeftToMove = nodesToMove - 2;
        } else {
            nodesLeftToMove = nodesToMove;
        }
        int availableNodes = currentDepth << 1;
        while (availableNodes > 0) {
            int lastNode = firstNode;
            if (firstNode > nodesToMove) {
                firstNode = first(array, lastNode - 1, nodesToMove);
            }
            int offset = 0;
            if (currentDepth >= insertDepth) {
                offset = Math.min(nodesLeftToMove, 1 << (currentDepth - insertDepth));
            } else if (currentDepth == insertDepth - 1) {
                offset = 1;
                if (array[firstNode] == lastNode) {
                    firstNode++;
                }
            }
            int i = availableNodes - ((lastNode - firstNode) + offset);
            int nextNode2 = nextNode;
            while (i > 0) {
                nextNode = nextNode2 - 1;
                array[nextNode2] = currentDepth;
                i--;
                nextNode2 = nextNode;
            }
            nodesLeftToMove -= offset;
            availableNodes = ((lastNode - firstNode) + offset) << 1;
            currentDepth++;
            nextNode = nextNode2;
        }
    }

    static void allocateHuffmanCodeLengths(int[] array, int maximumLength) {
        switch (array.length) {
            case 1:
                break;
            case 2:
                array[1] = 1;
                break;
            default:
                setExtendedParentPointers(array);
                int nodesToRelocate = findNodesToRelocate(array, maximumLength);
                if (array[0] % array.length >= nodesToRelocate) {
                    allocateNodeLengths(array);
                    return;
                } else {
                    allocateNodeLengthsWithRelocation(array, nodesToRelocate, maximumLength - (32 - Integer.numberOfLeadingZeros(nodesToRelocate - 1)));
                    return;
                }
        }
        array[0] = 1;
    }

    private Bzip2HuffmanAllocator() {
    }
}

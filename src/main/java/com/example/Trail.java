package com.example;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Trail {

    private final List<Vector3f> trailPoints = new ArrayList<>();
    private final Geometry trailGeo;
    private final Mesh trailMesh;
    private FloatBuffer vertexBuffer;
    private final Node rootNode;
    private final Material mat;

    public Trail(AssetManager assetManager, Node rootNode) {
        this.rootNode = rootNode;
        trailMesh = new Mesh();
        trailMesh.setMode(Mesh.Mode.Lines);
        trailMesh.setLineWidth(8); // Set the line width to make it thicker

        trailGeo = new Geometry("Trail", trailMesh);
        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);
        trailGeo.setMaterial(mat);
        rootNode.attachChild(trailGeo);
    }

    public void updateTrail(Vector3f position) {
        if (trailPoints.isEmpty() || !trailPoints.get(trailPoints.size() - 1).equals(position)) {
            trailPoints.add(position.clone());

            if (trailPoints.size() > 1) {
                int vertexCount = trailPoints.size() * 2;
                if (vertexBuffer == null || vertexBuffer.capacity() < vertexCount * 3) {
                    vertexBuffer = BufferUtils.createFloatBuffer(vertexCount * 3);
                    trailMesh.setBuffer(VertexBuffer.Type.Position, 3, vertexBuffer);
                } else {
                    vertexBuffer.clear();
                }

                for (int i = 1; i < trailPoints.size(); i++) {
                    vertexBuffer.put(trailPoints.get(i - 1).getX());
                    vertexBuffer.put(trailPoints.get(i - 1).getY());
                    vertexBuffer.put(trailPoints.get(i - 1).getZ());
                    vertexBuffer.put(trailPoints.get(i).getX());
                    vertexBuffer.put(trailPoints.get(i).getY());
                    vertexBuffer.put(trailPoints.get(i).getZ());
                }
                vertexBuffer.flip();
                trailMesh.updateBound();
                trailMesh.updateCounts();
            }
        }
    }
}

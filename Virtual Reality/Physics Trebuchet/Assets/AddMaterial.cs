using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AddMaterial : MonoBehaviour
{
    public Material terrainMaterial;

    // Start is called before the first frame update
    void Start()
    {
        Terrain terrain = GetComponent<Terrain>();
        if (terrain != null && terrainMaterial != null)
        {
            terrain.materialTemplate = terrainMaterial;
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}

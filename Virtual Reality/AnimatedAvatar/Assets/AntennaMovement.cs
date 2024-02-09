using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AntennaMovement : MonoBehaviour
{
    public Transform satellite;

    // Start is called before the first frame update
    void Start()
    {
 
    }

    // Update is called once per frame
    void Update()
    {
        var satellitePosition = satellite.position;
        transform.LookAt(new Vector3(-satellitePosition.x, satellitePosition.y, -satellitePosition.z));
    }
}

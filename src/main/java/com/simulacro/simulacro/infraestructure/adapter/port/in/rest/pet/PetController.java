//package com.simulacro.simulacro.infraestructure.adapter.port.in.rest.pet;
//
//@RestController
//@RequestMapping("/api/v1/pets")
//public class PetController {
//
//    private final RegisterPetUseCase registerPetUseCase;
//    private final OwnerRepository ownerRepository;
//
//    @PostMapping
//    public ResponseEntity<PetResponse> registerPet(
//            @Valid @RequestBody RegisterPetRequest request) {
//
//        // ✅ MAPPER 1: DTO → Domain Model
//        // Convierte objetos de HTTP a objetos de dominio
//        Pet pet = toDomain(request);
//
//        // Ejecutar caso de uso
//        Pet savedPet = registerPetUseCase.registerPet(
//                request.getPetName(),
//                Species.fromString(request.getSpecies()),
//                request.getBreed(),
//                new Age(request.getAge()),
//                request.getOwnerName(),
//                new Document(request.getOwnerDocument())
//        );
//
//        // ✅ MAPPER 2: Domain Model → DTO
//        // Convierte objetos de dominio a objetos de HTTP
//        PetResponse response = toDTO(savedPet);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
